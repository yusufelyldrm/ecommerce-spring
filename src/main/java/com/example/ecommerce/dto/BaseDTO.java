package com.example.ecommerce.dto;

import com.example.ecommerce.models.ProductVariant;

public abstract class BaseDTO {
    public abstract <T> T toEntity();

    public abstract <T> T toDTO(T entity);
}
