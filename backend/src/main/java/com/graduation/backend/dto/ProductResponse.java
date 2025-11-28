package com.graduation.backend.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String sku;
    private String description;
    private BigDecimal unitPrice;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
