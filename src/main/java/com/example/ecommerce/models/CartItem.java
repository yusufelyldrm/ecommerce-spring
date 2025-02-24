package com.example.ecommerce.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartItem extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    @Schema(description = "Product variant", required = true)
    private ProductVariant productVariant;

    @Schema(description = "Quantity", example = "1", required = true)
    private Integer quantity;

    @Schema(description = "Total price", example = "100", required = true)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    @Schema(description = "Cart", required = true)
    private Cart cart;
}
