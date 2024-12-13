package com.example.ecommerce.repository;

import com.example.ecommerce.models.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDataRepository extends JpaRepository<ProductData, Integer> {
    ProductData save(ProductData productData);
    ProductData findProductDataById(Long id);

    ProductData findProductDataByIdocNo(String idocNo);
}
