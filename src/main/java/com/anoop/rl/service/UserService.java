package com.anoop.rl.service;

import com.anoop.rl.model.ApiResponse;
import com.anoop.rl.model.UserEntity;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> getAllUsers();
    Optional<UserEntity> getUserById(Long id);
    ResponseEntity<UserEntity> getUserByUsername(String username);
    ResponseEntity<UserEntity> updateUser(Long userId, UserEntity user);
    ResponseEntity<ApiResponse> deleteUser(Long userId);
}
