package com.graduation.backend.service;

import com.graduation.backend.dto.AuthResponse;
import com.graduation.backend.dto.LoginRequest;

public interface AuthenticationService {
    AuthResponse login(LoginRequest request);
}

