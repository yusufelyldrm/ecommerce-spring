package com.example.ecommerce.dto;

import com.example.ecommerce.models.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartListDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("cart")
    private List<CartItemDTO> cartItemDTOS;

    @JsonProperty("totalPrice")
    private Long totalPrice;
}
