package com.example.ecommerce.controller;

import com.example.ecommerce.annotations.AdminOnly;
import com.example.ecommerce.annotations.Authenticated;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.VariantDTO;
import com.example.ecommerce.manager.AuthManager;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.ProductVariant;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.VariantRepository;
import com.example.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    @AdminOnly
    public ResponseEntity saveProducts(@RequestBody List<Product> products) {
        List<ProductDTO> productDTOs = productService.saveProducts(products);
        return ResponseEntity.ok(productDTOs);
    }

    @PostMapping("/variant")
    @Authenticated
    public ResponseEntity saveProductVariant(@RequestBody ProductVariant variant) {
        VariantDTO variantDTO = productService.saveProductVariant(variant);
        return ResponseEntity.ok(variantDTO);
    }

    @GetMapping("/{productId}")
    public ResponseEntity getProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.getProduct(productId);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/")
    public ResponseEntity getProducts() {
        List<ProductDTO> productDTOs = productService.getProducts();
        return ResponseEntity.ok(productDTOs);
    }

    @PutMapping("/{productId}")
    public ResponseEntity updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        ProductDTO productDTO = productService.updateProduct(productId, product);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Long productId) {
        String response = productService.deleteProduct(productId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}/variant/{variantId}")
    public ResponseEntity updateProductVariant(@PathVariable Long productId, @PathVariable Long variantId, @RequestBody ProductVariant variant) {
        ProductDTO productDTO = productService.updateProductVariant(productId, variantId, variant);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{productId}/variant/{variantId}")
    public ResponseEntity deleteProductVariant(@PathVariable Long productId, @PathVariable Long variantId) {
        String response = productService.deleteProductVariant(productId, variantId);
        return ResponseEntity.ok(response);
    }
}
