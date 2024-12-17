package com.example.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String email;
    private String username;
    private String password;

    @JsonProperty("is_admin")
    private boolean isAdmin;
}
