package com.graduation.backend.service;

import com.graduation.backend.dto.ProductCreateRequest;
import com.graduation.backend.dto.ProductResponse;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct (Long id, ProductCreateRequest request);
    void deactivateProduct (Long id);
}
