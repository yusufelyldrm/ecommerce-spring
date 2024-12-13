package com.example.ecommerce.repository;

import com.example.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product save(Product product);
    List<Product> findAllByProductDataId(Long id);
    Product findProductById(Long id);
}
