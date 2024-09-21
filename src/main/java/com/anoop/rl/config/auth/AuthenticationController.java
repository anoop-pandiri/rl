package com.anoop.rl.config.auth;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anoop.rl.exception.CustomException;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.model.enums.Role;

import jakarta.validation.Valid;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService){
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserEntity request){

        if (request.getRole() == Role.ADMIN) {
            throw new CustomException("Cannot assign ADMIN role without authorization.");
        }
        if (request.getPassword().length() < 8 || request.getPassword().length() > 20) {
            throw new IllegalArgumentException("Password must be between 8 and 20 characters");
        }

        String token = authService.register(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity request){
        
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Please enter Username!");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Please enter Password!");
        }

        Map<String, Object> response = authService.authenticate(request);
        String token = (String) response.get("token");
        Timestamp lastLogin = (Timestamp) response.get("lastLogin");

        return ResponseEntity.ok(Map.of("token", token, "lastLogin", lastLogin));
    }
}
