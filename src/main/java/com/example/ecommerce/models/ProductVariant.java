package com.example.ecommerce.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductVariant extends BaseEntity{

    @Schema(description = "Product variant code", example = "1234", required = true)
    private String code;

    @Schema(description = "Product variant stock", example = "100", required = true)
    private Integer stock;

    @Schema(description = "Product variant size", example = "M", required = true)
    private String size;

    @Schema(description = "Product variant price", example = "100", required = true)
    private Long price;

    //bir variant sadece bir pdoructa bağlı olabilir
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    public void updateWith(ProductVariant productVariant) {
        this.code = productVariant.getCode();
        this.stock = productVariant.getStock();
        this.size = productVariant.getSize();
        this.price = productVariant.getPrice();
    }
}
