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

    public String authenticateForLogin(String email, String password) throws Exception {
        User user = userRepository.findUserByEmail(email);

        boolean isValid = checkLoginCredentials(email, password, user);
        if (!isValid) {
            return "Invalid credentials";
        }

        if (user.getRole() == null) user.setRole(Role.USER);
        String token = generateToken(user.getEmail(), String.valueOf(user.getRole()));

        return token;
    }

    public boolean authenticate(Token token) throws Exception {
        Token tokenDb = tokenRepository.findByToken(token);
        if (tokenDb == null) {
            return false;
        }

        return !isTokenExpired(tokenDb.getToken());
    }

    public boolean checkLoginCredentials(String email, String password, User user) {
        return user != null && Objects.equals(password, user.getPassword());
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
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
}

