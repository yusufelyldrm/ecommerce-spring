package com.example.ecommerce.repository;

import com.example.ecommerce.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<ProductVariant, Integer> {
    ProductVariant save(ProductVariant variant);

    List<ProductVariant> findAllByProductId(Long id);

    ProductVariant findProductVariantById(Long id);
}
