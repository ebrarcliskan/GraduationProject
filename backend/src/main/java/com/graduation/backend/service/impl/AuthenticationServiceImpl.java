package com.graduation.backend.service.impl;

import com.graduation.backend.dto.AuthResponse;
import com.graduation.backend.dto.LoginRequest;
import com.graduation.backend.entity.User;
import com.graduation.backend.repository.UserRepository;
import com.graduation.backend.service.AuthenticationService;
import com.graduation.backend.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("Invalid username or password"));

            if (!user.isActive()) {
                throw new IllegalArgumentException("User account is inactive");
            }

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Invalid username or password");
            }

            String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

            Long branchId = null;
            String branchName = null;
            if (user.getBranch() != null) {
                branchId = user.getBranch().getId();
                branchName = user.getBranch().getName();
            }

            return AuthResponse.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .userId(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .branchId(branchId)
                    .branchName(branchName)
                    .build();
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            // Re-throw validation errors
            throw e;
        } catch (Exception e) {
            // Wrap unexpected errors
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }
}

