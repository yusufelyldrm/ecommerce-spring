package com.example.ecommerce.repository;

import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    CartItem findCartItemById(Integer id);
    CartItem findCartItemByProductVariantAndCart(ProductVariant variant, Cart cart);
    CartItem save(CartItem cartItem);
    void deleteById(Integer id);
}
