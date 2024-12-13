package com.example.ecommerce.dto;

import com.example.ecommerce.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductDataDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String idoc_no;

    @JsonProperty("products")
    private List<ProductDTO> products;
}

