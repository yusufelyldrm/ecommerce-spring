package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddressDTO;
import com.example.ecommerce.models.Address;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    //post address
    //get addresses
    //update address
    //delete address

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public void addAddress(AddressDTO addressDTO, User user) {
        Address address = addressDTO.toEntity();
        address.setUser(user);
        if (user.getAddressList() == null) {
            user.setAddressList(new ArrayList<>());
        }
        user.getAddressList().add(address);
        userRepository.save(user);
    }


    public void updateAddress(AddressDTO addressDTO, Long addressId) {
        Address address = addressRepository.findAddressById(addressId);
        address.updateWith(addressDTO);
        addressRepository.save(address);
    }

    public void deleteAddress(Long addressId, User user) {
        Address address = addressRepository.findAddressById(addressId);
        if (address.getUser().getId().equals(user.getId())) {
            addressRepository.deleteAddressById(addressId);
        }
    }

    public List<AddressDTO> getAddresses(User user) {
        List<Address> addresses = addressRepository.findAddressesByUserId(user.getId());
        List<AddressDTO> addressDTOs = new ArrayList<>();
        for (Address address : addresses) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.toDTO(address);
            if (address.getUser().equals(user)) {
                addressDTOs.add(addressDTO);
            }
        }
        return addressDTOs;
    }
}
