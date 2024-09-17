package com.anoop.rl.config.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthenticationResponse register(UserEntity request){
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user = repository.save(user);

        String token = jwtService.generateToken(user);

         return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(UserEntity request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserEntity user = repository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

         return new AuthenticationResponse(token);
    }
    
}
