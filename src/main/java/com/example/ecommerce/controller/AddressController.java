package com.example.ecommerce.controller;

import com.example.ecommerce.annotations.Authenticated;
import com.example.ecommerce.dto.AddressDTO;
import com.example.ecommerce.manager.AuthManager;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;
    private final AuthManager authManager;

    public AddressController(AddressService addressService, AddressRepository addressRepository, AuthManager authManager) {
        this.addressService = addressService;
        this.authManager = authManager;
    }

    @PostMapping("/add")
    @Authenticated
    public ResponseEntity addAddress(@RequestBody AddressDTO addressDTO, @RequestHeader("Authorization") String token) {
        addressService.addAddress(addressDTO, authManager.getUserFromToken(token));
        return ResponseEntity.ok("Address added successfully");
    }

    @PostMapping("/update/{addressId}")
    @Authenticated
    public ResponseEntity updateAddress(@RequestBody AddressDTO addressDTO, @PathVariable Long addressId) {
        addressService.updateAddress(addressDTO, addressId);
        return ResponseEntity.ok("Address updated successfully");
    }

    @PostMapping("/delete/{addressId}")
    @Authenticated
    public ResponseEntity deleteAddress(@PathVariable Long addressId, @RequestHeader("Authorization") String token) {
        User user = authManager.getUserFromToken(token);
        addressService.deleteAddress(addressId, user);
        return ResponseEntity.ok("Address deleted successfully");
    }

    @PostMapping("/get")
    @Authenticated
    public ResponseEntity getAddresses(@RequestHeader("Authorization") String token) {
        User user = authManager.getUserFromToken(token);
        return ResponseEntity.ok(addressService.getAddresses(user));
    }

}
