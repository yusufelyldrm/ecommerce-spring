package com.example.ecommerce.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartDTO {
    Long userId;
    Long productVariantId;
    Integer quantity;
    Long price;
}

