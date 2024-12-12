package com.example.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseDTO {
    @JsonProperty("token")
    private String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
