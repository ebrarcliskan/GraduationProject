package com.graduation.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BranchCreateRequest {
    @NotBlank
    @Size(min = 3, max = 120)
    private String name;

    @Size(max=255)
    private String address;

    @Size(max=50)
    private String phone;
}
