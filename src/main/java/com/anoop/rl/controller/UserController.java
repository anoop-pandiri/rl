package com.anoop.rl.controller;

import com.anoop.rl.model.ApiResponse;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.service.ApiResponseService;
import com.anoop.rl.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") Long userId, @RequestBody UserEntity user, Principal principal) {
        ResponseEntity<UserEntity> responseEntity = userService.getUserByUsername(principal.getName());
        UserEntity loggedInUser = responseEntity.getBody();
        
        if (!loggedInUser.getUserId().equals(userId)) {
            throw new AccessDeniedException("User does not have access to this resource");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Long userId, Principal principal) {

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

    @GetMapping("/export")
    public ResponseEntity<Resource> exportUserData(@RequestParam Long userId) throws JsonProcessingException {
        String userDataJson = userService.exportUserData(userId);

        ByteArrayResource resource = new ByteArrayResource(userDataJson.getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=user_data.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importUserData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty.");
        }

        try {
            String jsonData = new String(file.getBytes());
            userService.importUserData(jsonData);
            return ResponseEntity.ok("User data imported successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

    @GetMapping("/exportAll")
    public ResponseEntity<Resource> exportAllUsersData() throws JsonProcessingException {
        String allUsersDataJson = userService.exportAllUsersData(); // Get JSON for all users

        ByteArrayResource resource = new ByteArrayResource(allUsersDataJson.getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=all_users_data.json") // File name
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @PostMapping("/importAll")
    public ResponseEntity<String> importAllUsersData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty.");
        }

        try {
            String jsonData = new String(file.getBytes()); // Convert file content to string
            userService.importAllUsersData(jsonData); // Process JSON and save data
            return ResponseEntity.ok("All users data imported successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

}
