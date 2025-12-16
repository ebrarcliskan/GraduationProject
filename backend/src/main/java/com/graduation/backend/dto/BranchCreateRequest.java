package com.graduation.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BranchCreateRequest {
    @NotBlank(message = "Branch name cannot be blank")
    @Size(min = 3, max = 120, message = "Branch name must be between 3 and 120 characters")
    private String name;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Pattern(regexp = "^[0-9+\\-()\\s]*$", message = "Phone number contains invalid characters")
    @Size(max = 50, message = "Phone number must not exceed 50 characters")
    private String phone;
}
