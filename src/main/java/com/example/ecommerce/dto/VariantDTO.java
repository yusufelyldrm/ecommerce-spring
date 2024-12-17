package com.example.ecommerce.dto;

import com.example.ecommerce.models.ProductVariant;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantDTO extends BaseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("size")
    private String size;

    @JsonProperty("price")
    private Long price;

    @Override
    public VariantDTO toDTO(Object entity) {
        ProductVariant variant = (ProductVariant) entity;
        this.id = variant.getId();
        this.code = variant.getCode();
        this.stock = variant.getStock();
        this.size = variant.getSize();
        this.price = variant.getPrice();
        return this;
    }

    @Override
    public <T> T toEntity() {
        return null;
    }
}
