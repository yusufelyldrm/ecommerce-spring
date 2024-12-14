package com.example.ecommerce.controller;

import com.example.ecommerce.dto.*;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.VariantRepository;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    //sepete ekleme
    //sepetten silme
    //sepeti listeleme

    private final CartService cartService;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;


    public CartController(CartService cartService, UserService userService, ProductRepository productRepository, VariantRepository variantRepository) {
        this.cartService = cartService;
        this.userService = userService;
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
    }

    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody AddCartDTO addCartDTO) {
        cartService.addToCart(addCartDTO);
        return ResponseEntity.ok("Product added to cart successfully");
    }

    @PostMapping("/remove")
    public ResponseEntity removeFromCart(@RequestBody RemoveCartDTO removeCartDTO) {
        cartService.removeFromCart(removeCartDTO);
        return ResponseEntity.ok("Product removed from cart successfully");
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<CartListDTO> listCart(@PathVariable Integer userId) {
        Cart cart = cartService.listCart(userId);
        CartListDTO cartListDTO = new CartListDTO();
        CartItemDTO cartItemDTO = new CartItemDTO();
        VariantDTO variantDTO = new VariantDTO();
        ArrayList<CartItemDTO> cartItemDTOS = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            cartItemDTO.setId(cartItem.getId());
            cartItemDTO.setPrice(cartItem.getPrice());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setDescription(cartItem.getProductVariant().getProduct().getDescription());
            cartItemDTO.setColor(cartItem.getProductVariant().getProduct().getColor());

            variantDTO.setId(cartItem.getProductVariant().getId());
            variantDTO.setPrice(cartItem.getProductVariant().getPrice());
            variantDTO.setCode(cartItem.getProductVariant().getCode());
            variantDTO.setStock(cartItem.getProductVariant().getStock());
            variantDTO.setSize(cartItem.getProductVariant().getSize());

            cartItemDTO.setProductVariantDTO(variantDTO);

            cartItemDTOS.add(cartItemDTO);
        }

        cartListDTO.setId(cart.getId());
        cartListDTO.setTotalPrice(cart.getTotalPrice());

        cartListDTO.setCartItemDTOS(cartItemDTOS);

        return ResponseEntity.ok(cartListDTO);
    }

}
