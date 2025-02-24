package com.example.ecommerce.controller;

import com.example.ecommerce.models.Orders;
import com.example.ecommerce.models.User;
import com.example.ecommerce.service.OrderService;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Orders> createOrder(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(orderService.createOrder(currentUser));
    }

    @PostMapping("/{orderId}/place")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Orders> placeOrder(
        @PathVariable Long orderId,
        @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(orderService.placeOrder(orderId, currentUser));
    }

    @GetMapping("/code/{orderCode}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Orders> getOrderByCode(
        @PathVariable String orderCode,
        @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(orderService.getOrderByCode(orderCode, currentUser));
    }

    @GetMapping("/my-orders")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Orders>> getMyOrders(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(orderService.getAllOrdersForCustomer(currentUser));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Orders> getOrderById(
        @PathVariable Long orderId,
        @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(orderService.getOrderById(orderId, currentUser));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
} 