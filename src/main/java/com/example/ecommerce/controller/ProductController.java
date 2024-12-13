package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDataDTO;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.ProductData;
import com.example.ecommerce.models.ProductVariant;
import com.example.ecommerce.repository.ProductDataRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.VariantRepository;
import com.example.ecommerce.util.ConvertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductDataRepository productDataRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @PostMapping
    public ResponseEntity saveProductData(@RequestBody ProductData productData) {
        productDataRepository.save(productData);

        for (Product product : productData.getProducts()) {
            product.setProductData(productData);
            productRepository.save(product);

            for (ProductVariant variant : product.getVariants()) {
                variant.setProduct(product);
                variantRepository.save(variant);
            }
        }

        ProductData dbProductData = productDataRepository.findProductDataByIdocNo(productData.getIdocNo());
        ProductDataDTO productDataDTO = ConvertDTO.ProductDataToDTO(dbProductData);

        return ResponseEntity.ok(productDataDTO);
    }

    @GetMapping("/{productDataId}")
    public ResponseEntity<ProductDataDTO> getProductData(@PathVariable Long productDataId) {
        ProductData productData = productDataRepository.findProductDataById(productDataId);
        ProductDataDTO productDataDTO = ConvertDTO.ProductDataToDTO(productData);
        return ResponseEntity.ok(productDataDTO);
    }

    @PutMapping("/{idocCode}")
    public ResponseEntity<ProductData> updateProductData(@PathVariable String idocCode, @RequestBody ProductData updatedProductData) {
        ProductData productData = productDataRepository.findProductDataByIdocNo(idocCode);

        if (productData == null) {
            return ResponseEntity.notFound().build();
        }

        productData.setProducts(updatedProductData.getProducts());
        productDataRepository.save(productData);

        List<Product> updatedProducts = productData.getProducts();

        for (Product product : updatedProducts) {
            product.setProductData(productData);
            product = productRepository.save(product);

            for (ProductVariant variant : product.getVariants()) {
                variant.setProduct(product);
                variantRepository.save(variant);
            }
        }

        productDataRepository.save(productData);
        return ResponseEntity.ok(productData);
    }

    @DeleteMapping("/{idocCode}")
    public ResponseEntity deleteProductData(@PathVariable String idocCode) {
        ProductData productData = productDataRepository.findProductDataByIdocNo(idocCode);

        if (productData == null) {
            return ResponseEntity.notFound().build();
        }

        productDataRepository.delete(productData);
        return ResponseEntity.ok().build();
    }
}
