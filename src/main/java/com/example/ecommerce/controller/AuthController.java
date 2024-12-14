package com.example.ecommerce.controller;

import com.example.ecommerce.dto.LoginRequestDTO;
import com.example.ecommerce.dto.LoginResponseDTO;
import com.example.ecommerce.dto.RegisterRequestDTO;
import com.example.ecommerce.manager.AuthManager;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.User;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthManager authManager;
    private final CartService cartService;

    public AuthController(UserService userService, AuthManager authManager, CartService cartService) {
        this.userService = userService;
        this.authManager = authManager;
        this.cartService = cartService;
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
        user.setPassword(registerRequestDTO.getPassword());

        userService.saveUser(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0L);

        user.setCart(cart);
        userService.saveUser(user);

        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public LoginResponseDTO Login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        String token = authManager.authenticateForLogin(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        return new LoginResponseDTO(token);
    }
}
