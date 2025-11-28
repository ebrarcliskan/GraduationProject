package com.graduation.backend.repository;

import com.graduation.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    boolean existsBySku(String sku);
    Optional<Product> findBySku(String sku);
}
