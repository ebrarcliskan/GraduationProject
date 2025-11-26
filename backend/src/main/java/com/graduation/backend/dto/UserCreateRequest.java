package com.graduation.backend.dto;

import com.graduation.backend.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequest {

    @NotBlank
    @Size(min=3, max=50)
    private String username;

    @NotBlank
    @Size(min=3, max=100)
    private String fullName;

    @NotBlank
    @Email
    @Size(max=100)
    private String email;

    @NotBlank
    @Size(min=6, max=100)
    private String password;

    @NotNull
    private Role role;

    @NotNull(message = "Branch ID cannot be null")
    private Long branchId;

}
