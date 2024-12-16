package com.example.ecommerce.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Token id", example = "1", required = true)
    private int id;

    @Schema(description = "Token", required = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "User", required = true)
    private User user;

    public Token() {
    }

    public Token(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
