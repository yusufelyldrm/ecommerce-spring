package com.example.ecommerce.controller;

import com.example.ecommerce.annotations.Authenticated;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.manager.AuthManager;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.VariantRepository;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.util.ConvertDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    //sepete ekleme
    //sepetten silme
    //sepeti listeleme

    private final CartService cartService;


    public CartController(CartService cartService, UserService userService, ProductRepository productRepository, VariantRepository variantRepository, AuthManager authManager) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody AddCartDTO addCartDTO, @RequestHeader("Authorization") String token) {
        cartService.addToCart(addCartDTO, token);
        return ResponseEntity.ok("Product added to cart successfully");

    }

    @PostMapping("/remove")
    public ResponseEntity removeFromCart(@RequestBody RemoveCartDTO removeCartDTO, @RequestHeader("Authorization") String token) {
        cartService.removeFromCart(removeCartDTO, token);
        return ResponseEntity.ok("Product removed from cart successfully");
    }

    @GetMapping("/list")
    @Authenticated
    public ResponseEntity listCart(@RequestHeader("Authorization") String token) {
        Cart cart = cartService.listCart(token);
        CartListDTO cartListDTO = ConvertDTO.CartListToDTO(cart);
        return ResponseEntity.ok(cartListDTO);
    }

}
