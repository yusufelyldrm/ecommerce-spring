package com.example.ecommerce.repository;


import com.example.ecommerce.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address save(Address address);
    Address findAddressById(Long id);
    List<Address> findAddressesByUserId(Integer userId);
    void deleteAddressById(Long id);
}
