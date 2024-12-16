package com.example.ecommerce.util;

import com.example.ecommerce.dto.*;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.ProductVariant;
import com.sun.source.tree.VariableTree;

import java.util.ArrayList;
import java.util.List;

public class ConvertDTO {

    public static ProductDTO ProductToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setColor(product.getColor());
        productDTO.setGender(product.getGender());
        productDTO.setDescription(product.getDescription());

        VariantDTOList(product, productDTO);
        return productDTO;
    }

    public static VariantDTO VariantToDTO(ProductVariant variant) {
        VariantDTO variantDTO = new VariantDTO();
        variantDTO.setId(variant.getId());
        variantDTO.setCode(variant.getCode());
        variantDTO.setSize(variant.getSize());
        variantDTO.setStock(variant.getStock());
        variantDTO.setPrice(variant.getPrice());

        return variantDTO;
    }

    public static CartListDTO CartListToDTO(Cart cart){
        CartListDTO cartListDTO = new CartListDTO();
        CartItemDTO cartItemDTO = new CartItemDTO();
        ArrayList<CartItemDTO> cartItemDTOS = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
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

        cartListDTO.setId(cart.getId());
        cartListDTO.setTotalPrice(cart.getTotalPrice());

        cartListDTO.setCartItemDTOS(cartItemDTOS);

        return cartListDTO;
    }

    private static void VariantDTOList(Product product, ProductDTO productDTO) {
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

        productDTO.setVariants(variantDTOList);
    }
}
