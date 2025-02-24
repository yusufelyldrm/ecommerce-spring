package com.example.ecommerce.controller;

import com.example.ecommerce.dto.*;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.User;
import com.example.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity addToCart(
        @RequestBody AddCartDTO request,
        @AuthenticationPrincipal User currentUser
    ) {
        cartService.addToCart(request, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(cartService.getCartByUser(currentUser));
    }

    @DeleteMapping("/item/{cartItemId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> removeFromCart(
        @PathVariable Long cartItemId,
        @AuthenticationPrincipal User currentUser
    ) {
        cartService.removeFromCart(cartItemId, currentUser);
        return ResponseEntity.ok().build();
    }
}
