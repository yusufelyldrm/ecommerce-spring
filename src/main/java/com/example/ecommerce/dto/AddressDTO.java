package com.example.ecommerce.dto;

import com.example.ecommerce.models.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO extends BaseDTO {
    private String firstLine;
    private String secondLine;
    private String city;
    private String state;
    private String country;

    @Override
    public Address toEntity() {
        Address address = new Address();
        address.setFirstLine(this.firstLine);
        address.setSecondLine(this.secondLine);
        address.setCity(this.city);
        address.setState(this.state);
        address.setCountry(this.country);
        return address;
    }


    @Override
    public <T> T toDTO(T entity) {
        return null;
    }
}
