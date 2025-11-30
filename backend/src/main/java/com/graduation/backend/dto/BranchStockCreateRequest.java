package com.graduation.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BranchStockCreateRequest {

    @NotNull
    private Long branchId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(value = 0, message = "Quantity must be zero or positive")
    private Integer quantity;
}
