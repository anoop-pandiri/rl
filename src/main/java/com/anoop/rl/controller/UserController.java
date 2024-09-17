package com.anoop.rl.controller;

import com.anoop.rl.model.ApiResponse;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.service.ApiResponseService;
import com.anoop.rl.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApiResponseService apiResponseService;

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("username/{username}")
    public ResponseEntity<UserEntity> getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserEntity user) {
        ResponseEntity<ApiResponse> response = userService.createUser(user);

        ApiResponse apiResponse = response.getBody();
        apiResponseService.saveApiResponse(apiResponse);

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") Integer userId, @RequestBody UserEntity user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Integer userId) {
        ResponseEntity<ApiResponse> response = userService.deleteUser(userId);

        ApiResponse apiResponse = response.getBody();
        apiResponseService.saveApiResponse(apiResponse);

        return response;
    }

}
