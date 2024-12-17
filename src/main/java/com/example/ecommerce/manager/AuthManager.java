package com.example.ecommerce.manager;

import com.example.ecommerce.enums.Role;
import com.example.ecommerce.models.Token;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repository.TokenRepository;
import com.example.ecommerce.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthManager {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public AuthManager(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String authenticateForLogin(String email, String password) {
        User user = userRepository.findUserByEmail(email);

        boolean isValid = checkLoginCredentials(email, password, user);
        if (!isValid) {
            return "Invalid credentials";
        }

        if (user.getToken() != null) {
            Token token = tokenRepository.findTokenByToken(user.getToken().getToken());
            if (token != null && isTokenExpired(token.getToken())) {
                return token.getToken();
            }
        }

        String token = generateToken(user.getEmail(), String.valueOf(user.getRole()));

        Token newToken = new Token(token, user);
        user.setToken(newToken);

        tokenRepository.save(newToken);
        userRepository.save(user);

        return token;
    }

    public boolean authenticate(String token, Role expectedRole) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Token tokenDb = tokenRepository.findTokenByToken(token);
        String email = tokenDb.getUser().getEmail();

        if (expectedRole != null && !checkTokenValidation(tokenDb.getToken(), email, expectedRole)) {
            return false;
        }

        return isTokenExpired(token);
    }

    public boolean checkLoginCredentials(String email, String password, User user) {
        return user != null && Objects.equals(password, user.getPassword()) && Objects.equals(email, user.getEmail());
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            return !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkTokenValidation(String token, String email, Role role) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject().equals(email) && claims.get("role").equals(role.toString());
        } catch (Exception e) {
            return false;
        }
    }

    public User getUserFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            String email = claims.getSubject();
            return userRepository.findUserByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
}

