package com.example.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long id;
    private String description;
    private String name;
    private String gender;
    private String color;
    private String size;
    private String code;
    private Integer quantity;
    private Long totalPrice;
}
