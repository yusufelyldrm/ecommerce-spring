package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AddressDTO;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.service.AddressService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity addAddress(@RequestBody AddressDTO addressDTO, @AuthenticationPrincipal User user) {
        addressService.addAddress(addressDTO,user);
        return ResponseEntity.ok("Address added successfully");
    }

    @PostMapping("/update/{addressId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity updateAddress(@RequestBody AddressDTO addressDTO, @PathVariable Long addressId, @AuthenticationPrincipal User user) {
        String text = addressService.updateAddress(addressDTO, addressId, user);
        if(text.equals("Address not found") || text.equals("Address does not belong to user")) {
            return ResponseEntity.badRequest().body(text);
        }
        return ResponseEntity.ok("Address updated successfully");
    }

    @PostMapping("/delete/{addressId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity deleteAddress(@PathVariable Long addressId, @AuthenticationPrincipal User user) {
        addressService.deleteAddress(addressId, user);
        return ResponseEntity.ok("Address deleted successfully");
    }

    @PostMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getAddresses(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(addressService.getAddresses(user));
    }

}
