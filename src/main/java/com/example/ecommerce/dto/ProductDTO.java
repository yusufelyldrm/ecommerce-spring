package com.example.ecommerce.dto;

import com.example.ecommerce.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class ProductDTO extends BaseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("color")
    private String color;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("variants")
    private List<VariantDTO> variants;

    @Override
    public ProductDTO toDTO(Object entity) {
        Product product = (Product) entity;
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.color = product.getColor();
        this.gender = product.getGender();

        List<VariantDTO> variantDTOList = new ArrayList<>();
        product.getVariants().forEach(variant -> {
            VariantDTO variantDTO = new VariantDTO();
            variantDTO.setId(variant.getId());
            variantDTO.setCode(variant.getCode());
            variantDTO.setSize(variant.getSize());
            variantDTO.setStock(variant.getStock());
            variantDTO.setPrice(variant.getPrice());
            variantDTOList.add(variantDTO);
        });

        this.setVariants(variantDTOList);
        return this;
    }


    @Override
    public <T> T toEntity() {
        return null;
    }
}
