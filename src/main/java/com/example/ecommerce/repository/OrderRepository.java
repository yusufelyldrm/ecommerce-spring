package com.example.ecommerce.repository;

import com.example.ecommerce.models.Orders;
import com.example.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserOrderByOrderDateDesc(User user);
    Optional<Orders> findByOrderCode(String orderCode);
} 