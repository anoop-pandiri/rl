package com.anoop.rl.serviceimpl;

import com.anoop.rl.model.ApiResponse;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.repository.UserRepository;
import com.anoop.rl.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public ResponseEntity<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
public ResponseEntity<ApiResponse> createUser(UserEntity user) {
    Optional<UserEntity> existingUserByUsername = userRepository.findByUsername(user.getUsername());
    if (existingUserByUsername.isPresent()) {
        ApiResponse apiResponse = new ApiResponse(
            "/users",
            "User creation failed",
            400,
            "Username '" + user.getUsername() + "' is taken."
        );
        return ResponseEntity.status(400).body(apiResponse);
    }
    
    Optional<UserEntity> existingUserByEmail = userRepository.findByEmail(user.getEmail());
    if (existingUserByEmail.isPresent()) {
        ApiResponse apiResponse = new ApiResponse(
            "/users",
            "User creation failed",
            400,
            "An account with email '" + user.getEmail() + "' already exists."
        );
        return ResponseEntity.status(400).body(apiResponse);
    }

    UserEntity savedUser = userRepository.save(user);
    ApiResponse apiResponse = new ApiResponse(
        "/users/" + savedUser.getUserId(),
        "User created successfully",
        201,
        "User with name '"+ savedUser.getUsername() + "' and id '" + savedUser.getUserId() + "' created successfully."
    );
    return ResponseEntity.status(201).body(apiResponse);
}

    @Override
    public ResponseEntity<UserEntity> updateUser(Integer userId, UserEntity user) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setEmail(user.getEmail());
                    if (user.getPhone() != null) {
                        existingUser.setPhone(user.getPhone());
                    }
                    if (user.getRole() != null) {
                        existingUser.setRole(user.getRole());
                    }
                    return ResponseEntity.ok().body(userRepository.save(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteUser(Integer userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            ApiResponse apiResponse = new ApiResponse("/users/" + userId, "User deleted successfully", 204,
                    "User with ID " + userId + " has been deleted.");
            return ResponseEntity.status(204).body(apiResponse);
        } else {
            ApiResponse apiResponse = new ApiResponse("/users/" + userId, "User not found", 404,
                    "User with ID " + userId + " does not exist.");
            return ResponseEntity.status(404).body(apiResponse);
        }
    }
}
