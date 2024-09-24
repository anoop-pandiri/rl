package com.anoop.rl.tests;

import java.sql.Timestamp;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.anoop.rl.config.auth.AuthenticationController;
import com.anoop.rl.config.auth.AuthenticationService;
import com.anoop.rl.exception.GlobalExceptionHandler;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.model.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AuthenticationController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Arrange: Set up MockMvc with the AuthenticationController and GlobalExceptionHandler
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(authController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void register_ValidDetails_Success() throws Exception {
        // Arrange: Prepare a valid user and mock the service response
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@email.com");
        user.setRole(Role.USER);

        String token = "dummyToken";
        when(authService.register(any(UserEntity.class))).thenReturn(token);

        // Act: Perform the POST request to the /register endpoint
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                // Assert: Expect status 200 OK and the token in the response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    public void register_UsernameMissing_BadRequest() throws Exception {
        // Arrange: Create a user with a missing username
        UserEntity user = new UserEntity();
        user.setPassword("password123");
        user.setEmail("testuser@email.com");
        user.setRole(Role.USER);

        // Act: Perform the POST request
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                // Assert: Expect a 400 Bad Request and the appropriate error message
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("INV - Invalid value for username: Username cannot be null"));
    }

    @Test
    public void register_EmailMissing_BadRequest() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole(Role.USER);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("INV - Invalid value for email: Email cannot be null"));
    }

    @Test
    public void register_PasswordMissing_BadRequest() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("testuser@email.com");
        user.setRole(Role.USER);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("INV - Invalid value for password: Password cannot be null"));
    }

    @Test
    public void register_RoleMissing_BadRequest() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@email.com");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("INV - Invalid value for role: Role cannot be null"));
    }

    @Test
    public void register_PasswordTooShortOrTooLong_BadRequest() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("short");
        user.setEmail("testuser@email.com");
        user.setRole(Role.USER);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("ILL - Password must be between 8 and 20 characters"));
    }

    @Test
    public void register_EmailInvalid_BadRequest() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("invalid-email");
        user.setRole(Role.USER);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("INV - Invalid value for email: must be a well-formed email address"));
    }


    @Test
    public void register_AdminRole_BadRequest() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@email.com");
        user.setRole(Role.ADMIN); // Admin role

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("CE - Cannot assign ADMIN role without authorization."));
    }

    @Test
    public void login_ValidCredentials_Success() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password123");
    
        String token = "dummyToken";
        Timestamp lastLogin = new Timestamp(System.currentTimeMillis());
        when(authService.authenticate(any(UserEntity.class))).thenReturn(Map.of("token", token, "lastLogin", lastLogin));
    
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.lastLogin").exists());
    }
    

    @Test
    public void login_UsernameMissing_BadRequest() throws Exception {
        UserEntity user = new UserEntity();
        user.setPassword("password123");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("ILL - Please enter Username!"));
    }

    @Test
    public void login_PasswordMissing_BadRequest() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("ILL - Please enter Password!"));
    }

}
