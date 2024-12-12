package com.example.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "tokens") 
public class Token {

    @Id
    private int id;
    private String token;

}
