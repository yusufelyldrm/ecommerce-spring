package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddCartDTO;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.ProductVariant;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.VariantRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final VariantRepository variantRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, UserService userService, VariantRepository variantRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userService = userService;
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

    public void clearCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());
        cart.getCartItems().clear();
        cart.setTotalPrice(0L);
        cartRepository.save(cart);
    }

    public Cart getCartByUser(User user) {
        return cartRepository.findByUserId(user.getId());
    }

    public void addToCart(AddCartDTO addCartDTO, User user) {
        Cart cart = cartRepository.findByUserId(user.getId());

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

    public void removeFromCart(Long productVariantId, User user) {
        Cart cart = cartRepository.findCartByUserEmail(user.getEmail());

        ProductVariant productVariant = variantRepository.findProductVariantById(productVariantId);
        CartItem cartItem = cartItemRepository.findCartItemByProductVariantAndCart(productVariant, cart);

        if (cartItem == null) {
            throw new IllegalArgumentException("Item not found in cart for product variant ID: " + productVariantId);
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

    public Cart listCart(User user) {
        return cartRepository.findByUserId(user.getId());
    }


    public Long calculateCartTotalPrice(Cart cart) {
        long totalPrice = 0L;
        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getProductVariant().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}
