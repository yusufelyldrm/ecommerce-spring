package com.example.ecommerce.models;

import com.example.ecommerce.dto.AddressDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address extends BaseEntity {
    private String firstLine; //  ör: 221.sk atakent mahallesi
    private String secondLine; // ör : blabla apartmanı B blok daire 55

    private String description; // ör : carfeour karşısı

    private String city; //il
    private String state; //ilçe
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateWith(AddressDTO addressDTO) {
        this.firstLine = addressDTO.getFirstLine();
        this.secondLine = addressDTO.getSecondLine();
        this.city = addressDTO.getCity();
        this.state = addressDTO.getState();
        this.country = addressDTO.getCountry();
    }
}
