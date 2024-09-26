package com.anoop.rl.controller;

import com.anoop.rl.model.ApiResponse;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.service.ApiResponseService;
import com.anoop.rl.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApiResponseService apiResponseService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, ApiResponseService apiResponseService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.apiResponseService = apiResponseService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserEntity> getUserByUsername(@PathVariable("username") String username) {
        ResponseEntity<UserEntity> re = userService.getUserByUsername(username);
        UserEntity user = re.getBody();

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setPassword(null);
        user.setUserId(null);
        user.setRole(null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<UserEntity> getUserProfile(@PathVariable("username") String username, Principal principal) {
        ResponseEntity<UserEntity> responseEntity = userService.getUserByUsername(principal.getName());
        UserEntity loggedInUser = responseEntity.getBody();

        if (!loggedInUser.getUserId().equals(userService.getUserByUsername(username).getBody().getUserId())) {
            throw new AccessDeniedException("");
        }
        ResponseEntity<UserEntity> re = userService.getUserByUsername(username);
        UserEntity user = re.getBody();

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") Integer userId, @RequestBody UserEntity user, Principal principal) {
        ResponseEntity<UserEntity> responseEntity = userService.getUserByUsername(principal.getName());
        UserEntity loggedInUser = responseEntity.getBody();
        
        if (!loggedInUser.getUserId().equals(userId)) {
            throw new AccessDeniedException("User does not have access to this resource");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Integer userId, Principal principal) {

        ResponseEntity<UserEntity> responseEntity = userService.getUserByUsername(principal.getName());
        UserEntity loggedInUser = responseEntity.getBody();

        if (!loggedInUser.getUserId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to delete this account.");
        }

        ResponseEntity<ApiResponse> response = userService.deleteUser(userId);

        ApiResponse apiResponse = response.getBody();
        apiResponseService.saveApiResponse(apiResponse);

        return response;
    }

}
