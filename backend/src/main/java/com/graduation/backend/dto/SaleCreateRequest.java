package com.graduation.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleCreateRequest {

    @NotNull
    private Long branchId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(value=1, message="Quantity must be least 1")
    private Integer quantity;
}
