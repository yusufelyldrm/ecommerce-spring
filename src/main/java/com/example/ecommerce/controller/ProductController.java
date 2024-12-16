package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.VariantDTO;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.manager.AuthManager;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.ProductVariant;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.VariantRepository;
import com.example.ecommerce.util.ConvertDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductRepository productRepository;


    private final VariantRepository variantRepository;

    private final AuthManager authManager;

    public ProductController(ProductRepository productRepository, VariantRepository variantRepository, AuthManager authManager) {
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
        this.authManager = authManager;
    }

    @PostMapping("/save")
    public ResponseEntity saveProducts(@RequestBody List<Product> products, @RequestHeader("Authorization") String token) throws Exception {
        boolean is_auth = authManager.authenticate(token, Role.ADMIN);
        if (!is_auth) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }
        for (Product product : products) {
            productRepository.save(product);

            for (ProductVariant variant : product.getVariants()) {
                variant.setProduct(product);
                variantRepository.save(variant);
            }
        }

        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = ConvertDTO.ProductToDTO(product);
            productDTOs.add(productDTO);
        }

        return ResponseEntity.ok(productDTOs);
    }

    @PostMapping("/variant")
    public ResponseEntity saveProductVariant(@RequestBody ProductVariant variant, @RequestHeader("Authorization") String token) throws Exception {
        boolean is_auth = authManager.authenticate(token, Role.ADMIN);
        if (!is_auth) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }
        variantRepository.save(variant);
        VariantDTO variantDTO = ConvertDTO.VariantToDTO(variant);
        return ResponseEntity.ok(variantDTO);
    }

    @GetMapping("/{productId}")
    public ResponseEntity getProduct(@PathVariable Long productId, @RequestHeader("Authorization") String token) throws Exception {
        boolean is_auth = authManager.authenticate(token, null);
        if (!is_auth) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }
        Product product = productRepository.findProductById(productId);
        ProductDTO productDTO = ConvertDTO.ProductToDTO(product);

        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/")
    public ResponseEntity getProducts(@RequestHeader("Authorization") String token) throws Exception {
        boolean is_auth = authManager.authenticate(token, null);
        if (!is_auth) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = ConvertDTO.ProductToDTO(product);
            productDTOs.add(productDTO);
        }

        return ResponseEntity.ok(productDTOs);
    }

    @PutMapping("/{productId}")
    public ResponseEntity updateProduct(@PathVariable Long productId, @RequestBody Product product, @RequestHeader("Authorization") String token) throws Exception {
        boolean is_auth = authManager.authenticate(token, Role.ADMIN);
        if (!is_auth) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }
        Product dbProduct = productRepository.findProductById(productId);
        dbProduct.setName(product.getName());
        dbProduct.setColor(product.getColor());
        dbProduct.setGender(product.getGender());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setVariants(product.getVariants());

        productRepository.save(dbProduct);

        ProductDTO productDTO = ConvertDTO.ProductToDTO(dbProduct);

        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Long productId, @RequestHeader("Authorization") String token) throws Exception {
        boolean is_auth = authManager.authenticate(token, null);
        if (!is_auth) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }
        Product product = productRepository.findProductById(productId);
        productRepository.delete(product);

        return ResponseEntity.ok("Product deleted successfully");
    }

    @PutMapping("/{productId}/variant/{variantId}")
    public ResponseEntity updateProductVariant(@PathVariable Long productId, @PathVariable Long variantId, @RequestBody ProductVariant variant, @RequestHeader("Authorization") String token) throws Exception {
        boolean is_auth = authManager.authenticate(token, null);
        if (!is_auth) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }
        Product dbProduct = productRepository.findProductById(productId);
        ProductVariant dbVariant = variantRepository.findProductVariantById(variantId);

        dbVariant.setCode(variant.getCode());
        dbVariant.setStock(variant.getStock());
        dbVariant.setSize(variant.getSize());
        dbVariant.setPrice(variant.getPrice());

        variantRepository.save(dbVariant);

        ProductDTO productDTO = ConvertDTO.ProductToDTO(dbProduct);

        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{productId}/variant/{variantId}")
    public ResponseEntity deleteProductVariant(@PathVariable Long productId, @PathVariable Long variantId, @RequestHeader("Authorization") String token) throws Exception {
        boolean is_auth = authManager.authenticate(token, null);
        if (!is_auth) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }
        Product dbProduct = productRepository.findProductById(productId);
        ProductVariant dbVariant = variantRepository.findProductVariantById(variantId);

        dbProduct.getVariants().remove(dbVariant);
        productRepository.save(dbProduct);

        variantRepository.delete(dbVariant);

        return ResponseEntity.ok("Variant deleted successfully");
    }
}
