package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.VariantDTO;
import com.example.ecommerce.manager.AuthManager;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.ProductVariant;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.VariantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;

    public ProductService(ProductRepository productRepository, VariantRepository variantRepository, AuthManager authManager) {
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
    }

    public List<ProductDTO> saveProducts(List<Product> products) {
        for (Product product : products) {
            productRepository.save(product);
            for (ProductVariant variant : product.getVariants()) {
                variant.setProduct(product);
                variantRepository.save(variant);
            }
        }

        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.toDTO(product);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    public VariantDTO saveProductVariant(ProductVariant variant) {
        variantRepository.save(variant);
        VariantDTO variantDTO = new VariantDTO();
        return variantDTO.toDTO(variant);
    }

    public ProductDTO getProduct(Long productId) {
        Product product = productRepository.findProductById(productId);
        ProductDTO productDTO = new ProductDTO();
        return productDTO.toDTO(product);
    }

    public List<ProductDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.toDTO(product);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    public ProductDTO updateProduct(Long productId, Product product) {
        Product dbProduct = productRepository.findProductById(productId);
        dbProduct.updateWith(product);
        productRepository.save(dbProduct);
        ProductDTO productDTO = new ProductDTO();
        return productDTO.toDTO(dbProduct);
    }

    public String deleteProduct(Long productId) {
        Product product = productRepository.findProductById(productId);
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    public ProductDTO updateProductVariant(Long productId, Long variantId, ProductVariant variant) {
        Product dbProduct = productRepository.findProductById(productId);
        ProductVariant dbVariant = variantRepository.findProductVariantById(variantId);

        dbVariant.updateWith(variant);
        variantRepository.save(dbVariant);

        ProductDTO productDTO = new ProductDTO();
        return productDTO.toDTO(dbProduct);
    }

    public String deleteProductVariant(Long productId, Long variantId) {
        Product dbProduct = productRepository.findProductById(productId);
        ProductVariant dbVariant = variantRepository.findProductVariantById(variantId);

        dbProduct.getVariants().remove(dbVariant);
        productRepository.save(dbProduct);
        variantRepository.delete(dbVariant);

        return "Variant deleted successfully";
    }
}
