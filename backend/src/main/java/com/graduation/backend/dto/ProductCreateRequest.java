package com.graduation.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
    @NotBlank
    @Size(min=2, max=150)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String sku;

    @Size(max = 255)
    private String description;

    @NotNull
    @DecimalMin(value="0.0", inclusive = false, message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;
}
