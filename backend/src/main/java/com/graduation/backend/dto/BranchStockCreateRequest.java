package com.graduation.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BranchStockCreateRequest {

    @NotNull(message = "Branch ID cannot be null")
    private Long branchId;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be zero or positive")
    @Max(value = 1000000, message = "Quantity cannot exceed 1000000")
    private Integer quantity;
}
