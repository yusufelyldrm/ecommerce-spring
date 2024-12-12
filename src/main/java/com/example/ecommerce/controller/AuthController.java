package com.example.ecommerce.controller;

import com.example.ecommerce.dto.LoginRequestDTO;
import com.example.ecommerce.dto.RegisterRequestDTO;
import com.example.ecommerce.manager.AuthManager;
import com.example.ecommerce.models.User;
import com.example.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthManager authManager;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, AuthManager authManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
    }


    @PostMapping("/register")
    public ResponseEntity<String> Register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        User user = userService.findByEmail(registerRequestDTO.getEmail());
        if (user != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        user = new User();
        user.setEmail(registerRequestDTO.getEmail());
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        userService.saveUser(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        String token = authManager.authenticateForLogin(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        return ResponseEntity.ok(token);
    }
}
