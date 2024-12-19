package com.anoop.rl.serviceimpl;

import com.anoop.rl.model.ApiResponse;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.repository.UserRepository;
import com.anoop.rl.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public ResponseEntity<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UserEntity> updateUser(Long userId, UserEntity user) {
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
    public ResponseEntity<ApiResponse> deleteUser(Long userId) {
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

    @Override
    public String exportUserData(Long userId) throws JsonProcessingException {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Use Jackson ObjectMapper to serialize the user data into JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
    }

    @Override
    public void importUserData(String jsonData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize JSON into User object
        UserEntity user = mapper.readValue(jsonData, UserEntity.class);

        // Save the user object and its entire hierarchy (thanks to cascading)
        userRepository.save(user);
    }

    @Override
    public String exportAllUsersData() throws JsonProcessingException {
        List<UserEntity> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new RuntimeException("No users found to export");
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allUsers);
    }

    @Override
    public void importAllUsersData(String jsonData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // Parse the JSON string into a list of UserEntity objects
        List<UserEntity> users = mapper.readValue(jsonData, mapper.getTypeFactory().constructCollectionType(List.class, UserEntity.class));

        // Save all users to the database
        userRepository.saveAll(users);
    }
}
