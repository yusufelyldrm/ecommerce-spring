package com.example.ecommerce.util;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.ProductDataDTO;
import com.example.ecommerce.dto.VariantDTO;
import com.example.ecommerce.models.ProductData;

import java.util.ArrayList;
import java.util.List;

public class ConvertDTO {
    public static ProductDataDTO ProductDataToDTO(ProductData productData) {
        ProductDataDTO productDataDTO = new ProductDataDTO();
        productDataDTO.setId(productData.getId());
        productDataDTO.setIdoc_no(productData.getIdocNo());

        List<ProductDTO> productDTOList = new ArrayList<>();

        productData.getProducts().forEach(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setColor(product.getColor());
            productDTO.setGender(product.getGender());

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
            productDTOList.add(productDTO);
        });

        productDataDTO.setProducts(productDTOList);
        return productDataDTO;
    }

}
