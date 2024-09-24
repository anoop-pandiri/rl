package com.anoop.rl.config.auth;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.anoop.rl.config.jwt.JwtService;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.repository.UserRepository;

@Service
public class AuthenticationService {
    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String register(UserEntity request){
        boolean usernameExists = repository.existsByUsername(request.getUsername());
        boolean emailExists = repository.existsByEmail(request.getEmail());
        boolean phoneExists = repository.existsByPhone(request.getPhone());
        
        if (usernameExists && emailExists) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "CON - " + "Username and email already exist");
    } else if (usernameExists) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "CON - " + "Username already exists");
    } else if (emailExists) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "CON - " + "Email already exists");
    }
    else if(phoneExists){
        throw new ResponseStatusException(HttpStatus.CONFLICT, "CON - " + "Phone already exists");
    }
  
        try{
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setPhone(request.getPhone());
        user.setLastLogin(Timestamp.valueOf(LocalDateTime.now()));
        user = repository.save(user);

        return jwtService.generateToken(user);
        }
        catch (Exception e) {
            throw new RuntimeException("RE - " + "Unexpected error occurred during registration", e);
        }
    }

    public Map<String, Object> authenticate(UserEntity request) {
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserEntity user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Timestamp lastLogin = user.getLastLogin();
        user.setLastLogin(Timestamp.valueOf(LocalDateTime.now()));
        user = repository.save(user);
        return Map.of("token", jwtService.generateToken(user), "lastLogin", lastLogin);
        }
        catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
}
