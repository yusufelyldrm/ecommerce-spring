package com.example.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveCartDTO {
    private String email;
    private Long productVariantId;
}
