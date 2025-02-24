package com.example.ecommerce.service;

import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.OrderItem;
import com.example.ecommerce.models.Orders;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public Orders createOrder(User user) {
        Cart cart = cartService.getCartByUser(user);
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Orders order = new Orders();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(calculateTotalAmount(cart));

        List<OrderItem> orderItems = cart.getCartItems().stream()
            .map(cartItem -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(cartItem.getProductVariant().getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(BigDecimal.valueOf(cartItem.getProductVariant().getPrice()));
                return orderItem;
            })
            .collect(Collectors.toList());
        
        order.setItems(orderItems);

        cartService.clearCart(user);
        
        return orderRepository.save(order);
    }

    public Orders placeOrder(Long orderId, User user) {
        Orders order = getOrderById(orderId, user);
        
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order cannot be placed because it's not in PENDING status");
        }
        
        order.setStatus(OrderStatus.PLACED);
        order.setPlacedDate(LocalDateTime.now());
        
        return orderRepository.save(order);
    }

    public Orders getOrderByCode(String orderCode, User user) {
        Orders order = orderRepository.findByOrderCode(orderCode)
            .orElseThrow(() -> new RuntimeException("Order not found with code: " + orderCode));

        if (!order.getUser().getId().equals(user.getId()) && 
            !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Not authorized to view this order");
        }
        
        return order;
    }

    public List<Orders> getAllOrdersForCustomer(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public Orders getOrderById(Long orderId, User user) {
        Orders order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (!order.getUser().getId().equals(user.getId()) && 
            !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Not authorized to view this order");
        }
        
        return order;
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    private BigDecimal calculateTotalAmount(Cart cart) {
        return cart.getCartItems().stream()
            .map(cartItem -> BigDecimal.valueOf(cartItem.getProductVariant().getPrice()).multiply(BigDecimal.valueOf(cartItem.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 