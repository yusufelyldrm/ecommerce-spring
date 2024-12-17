package com.example.ecommerce.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartDTO{
    Long productVariantId;
    Integer quantity;
}

