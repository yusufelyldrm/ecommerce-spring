package com.example.ecommerce.repository;

import com.example.ecommerce.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserId(Integer userId);
    Cart save(Cart cart);
    void deleteById(Integer id);

    Cart findCartByUserEmail(String email);
}
