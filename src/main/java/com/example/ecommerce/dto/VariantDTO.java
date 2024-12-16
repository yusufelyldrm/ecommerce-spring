package com.example.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantDTO {
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
}
