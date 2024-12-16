package com.example.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Product id", example = "1", required = true)
    private Long id;

    @Schema(description = "Product name", example = "T-shirt", required = true)
    private String name;

    @Schema(description = "Product description", example = "A comfortable t-shirt", required = true)
    private String description;

    @Schema(description = "Product color", example = "Red", required = true)
    private String color;

    @Schema(description = "Gender", example = "MALE", required = true)
    private String gender;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Product variants", required = true)
    private List<ProductVariant> variants;

    public void updateWith(Product product) {
        this.name = product.getName();
        this.color = product.getColor();
        this.gender = product.getGender();
        this.description = product.getDescription();
        this.variants = product.getVariants();
    }
}
