package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddCartDTO;
import com.example.ecommerce.dto.RemoveCartDTO;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.ProductVariant;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.VariantRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, UserService userService, ProductRepository productRepository, VariantRepository variantRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart createCart(String email) {
        Cart cart = new Cart();
        cart.setUser(userService.findByEmail(email));
        cart.setTotalPrice(0L);
        cartRepository.save(cart);

        return cart;
    }

    public void addToCart(AddCartDTO addCartDTO) {
        Cart cart = cartRepository.findByUserId(Math.toIntExact(addCartDTO.getUserId()));

        ProductVariant variant = variantRepository.findProductVariantById(addCartDTO.getProductVariantId());

        CartItem cartItem = cartItemRepository.findCartItemByProductVariantAndCart(variant, cart);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProductVariant(variant);
            cartItem.setQuantity(addCartDTO.getQuantity());
            cartItem.setPrice(variant.getPrice() * addCartDTO.getQuantity());
            cartItem.setCart(cart);

            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + addCartDTO.getQuantity());
            cartItem.setPrice(variant.getPrice() * cartItem.getQuantity());
        }

        cart.setTotalPrice(calculateCartTotalPrice(cart));
        cartRepository.save(cart);
    }

    public void removeFromCart(RemoveCartDTO removeCartDTO) {
        Cart cart = cartRepository.findCartByUserEmail(removeCartDTO.getEmail());

        ProductVariant productVariant = variantRepository.findProductVariantById(removeCartDTO.getProductVariantId());
        CartItem cartItem = cartItemRepository.findCartItemByProductVariantAndCart(productVariant, cart);

        if (cartItem == null) {
            throw new IllegalArgumentException("Item not found in cart for product variant ID: " + removeCartDTO.getProductVariantId());
        }

        cartItem.setQuantity(cartItem.getQuantity() - 1);
        if (cartItem.getQuantity() > 0) {
            cartItem.setPrice(productVariant.getPrice() * cartItem.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }

        cart.setTotalPrice(calculateCartTotalPrice(cart));
        cartRepository.save(cart);
    }

    public Cart listCart(Integer userId) {
        return cartRepository.findByUserId(Math.toIntExact(userId));
    }


    public Long calculateCartTotalPrice(Cart cart) {
        long totalPrice = 0L;
        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getProductVariant().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}
