package com.graduation.backend.service.impl;

import com.graduation.backend.dto.UserCreateRequest;
import com.graduation.backend.dto.UserResponse;
import com.graduation.backend.entity.Branch;
import com.graduation.backend.entity.User;
import com.graduation.backend.repository.BranchRepository;
import com.graduation.backend.repository.UserRepository;
import com.graduation.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    @Override
    public UserResponse createUser(UserCreateRequest request) {

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + request.getBranchId()));

        User user = User.builder()
                 .username(request.getUsername())
                 .fullName(request.getFullName())
                 .email(request.getEmail())
                 .password(request.getPassword())
                 .role(request.getRole())
                 .active(true)
                 .branch(branch)
                 .build();
         User saved = userRepository.save(user);
         return toResponse(saved);
    }

    @Override
    @Transactional(readOnly=true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return toResponse(user);
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        Long branchId = null;
        String branchName = null;

        if (user.getBranch() != null) {
            branchId = user.getBranch().getId();
            branchName = user.getBranch().getName();
        }
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .branchId(branchId)
                .branchName(branchName)
                .build();
    }
}
