package com.graduation.backend.service.impl;

import com.graduation.backend.dto.ProductCreateRequest;
import com.graduation.backend.dto.ProductResponse;
import com.graduation.backend.entity.Product;
import com.graduation.backend.repository.ProductRepository;
import com.graduation.backend.service.ProductService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new EntityExistsException("Product already exists with sku: " + request.getSku());
        }

        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .description(request.getDescription())
                .unitPrice(request.getUnitPrice())
                .active(true)
                .build();

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductCreateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setDescription(request.getDescription());
        product.setUnitPrice(request.getUnitPrice());

        Product updated = productRepository.save(product);
        return toResponse(updated);
    }

    @Override
    public void deactivateProduct (Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        product.setActive(false);
        productRepository.save(product);
    }

    private ProductResponse toResponse (Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .active(product.isActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}















