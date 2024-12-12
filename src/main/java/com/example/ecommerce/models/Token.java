package com.example.ecommerce.models;

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
    private int id;
    private String token;
    public Token() {
    }

    public Token(String token) {
        this.token = token;
    }
}
