package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.VariantDTO;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.ProductVariant;
import com.example.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Product management APIs")
public class ProductController {
    private final ProductService productService;

    @Operation(
        summary = "Get all products",
        description = "Retrieves a list of all available products"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved products",
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = Product.class))
        )
    )
    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public ResponseEntity getProducts() {
        List<ProductDTO> productDTOs = productService.getProducts();
        return ResponseEntity.ok(productDTOs);
    }

    @Operation(
        summary = "Create a new product",
        description = "Creates a new product with the provided details"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Product created successfully"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid input"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity saveProducts(@RequestBody List<Product> products) {
        List<ProductDTO> productDTOs = productService.saveProducts(products);
        return ResponseEntity.ok(productDTOs);
    }

    @PostMapping("/variant")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity saveProductVariant(@RequestBody ProductVariant variant) {
        VariantDTO variantDTO = productService.saveProductVariant(variant);
        return ResponseEntity.ok(variantDTO);
    }

    @GetMapping("/{productId}")
    public ResponseEntity getProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.getProduct(productId);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        ProductDTO productDTO = productService.updateProduct(productId, product);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteProduct(@PathVariable Long productId) {
        String response = productService.deleteProduct(productId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}/variant/{variantId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateProductVariant(@PathVariable Long productId, @PathVariable Long variantId, @RequestBody ProductVariant variant) {
        ProductDTO productDTO = productService.updateProductVariant(productId, variantId, variant);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{productId}/variant/{variantId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteProductVariant(@PathVariable Long productId, @PathVariable Long variantId) {
        String response = productService.deleteProductVariant(productId, variantId);
        return ResponseEntity.ok(response);
    }
}
