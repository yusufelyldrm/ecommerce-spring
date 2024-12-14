package com.example.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long id;
    private Integer quantity;
    private String description;
    private String color;
    private Long price;
    private VariantDTO productVariantDTO;
}
