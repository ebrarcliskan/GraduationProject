package com.graduation.backend.service;

import com.graduation.backend.dto.UserCreateRequest;
import com.graduation.backend.dto.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();

    void deleteUser(Long id);

}
