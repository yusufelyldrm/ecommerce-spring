package com.example.ecommerce.controller;

import com.example.ecommerce.annotations.Authenticated;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody AddCartDTO addCartDTO, @RequestHeader("Authorization") String token) {
        cartService.addToCart(addCartDTO, token);
        return ResponseEntity.ok("Product added to cart successfully");
    }

    @PostMapping("/remove/{productVariantId}")
    public ResponseEntity removeFromCart(@PathVariable Long productVariantId, @RequestHeader("Authorization") String token) {
        cartService.removeFromCart(productVariantId, token);
        return ResponseEntity.ok("Product removed from cart successfully");
    }

    @GetMapping("/list")
    @Authenticated
    public ResponseEntity listCart(@RequestHeader("Authorization") String token) {
        Cart cart = cartService.listCart(token);
        CartListDTO cartListDTO = new CartListDTO();
        cartListDTO.toDTO(cart);
        return ResponseEntity.ok(cartListDTO);
    }
}
