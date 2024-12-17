package com.example.ecommerce.dto;

import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartListDTO extends BaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("cart")
    private List<CartItemDTO> cartItemDTOS;

    @JsonProperty("totalPrice")
    private Long totalPrice;

    @Override
    public <T> T toEntity() {
        return null;
    }

    @Override
    public CartListDTO toDTO(Object entity) {
        Cart cart = (Cart) entity;

        ArrayList<CartItemDTO> cartItemDTOS = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(cartItem.getId());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setDescription(cartItem.getProductVariant().getProduct().getDescription());
            cartItemDTO.setColor(cartItem.getProductVariant().getProduct().getColor());
            cartItemDTO.setGender(cartItem.getProductVariant().getProduct().getGender());
            cartItemDTO.setName(cartItem.getProductVariant().getProduct().getName());
            cartItemDTO.setSize(cartItem.getProductVariant().getSize());
            cartItemDTO.setCode(cartItem.getProductVariant().getCode());
            cartItemDTO.setTotalPrice(cartItem.getPrice());

            cartItemDTOS.add(cartItemDTO);
        }

        this.setId(cart.getId());
        this.setTotalPrice(cart.getTotalPrice());

        this.setCartItemDTOS(cartItemDTOS);

        return this;
    }
}
