package com.example.ecommerce.manager;

import com.example.ecommerce.models.Token;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repository.TokenRepository;
import com.example.ecommerce.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthManager {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private String jwtExpirationMs;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public AuthManager(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
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

        return generateToken(user.getUsername(), String.valueOf(user.getRole()));
    }

    public boolean authenticate(Token token) throws Exception {
        Token tokenDb = tokenRepository.findByToken(token);
        if (tokenDb == null) {
            return false;
        }

        if (isTokenExpired(tokenDb.getToken())) {
            return false;
        }

        return true;
    }

    public boolean checkLoginCredentials(String email, String password, User user) {
        return user != null && passwordEncoder.matches(password, user.getPassword());
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
}

