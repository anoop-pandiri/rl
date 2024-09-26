package com.anoop.rl.tests;

import com.anoop.rl.model.UserEntity;
import com.anoop.rl.model.enums.Role;
import com.anoop.rl.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Rolls back changes after each test
public class AuthenticationControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Optionally clear the database before each test or set up test data
        userRepository.deleteAll();
    }

    @Test
    public void register_ValidDetails_Success() throws Exception {
        // Arrange: Create a user entity object
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@email.com");
        user.setRole(Role.USER);

        // Act: Perform POST request to /register
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty()); // Ensure token is returned

        // Assert: Verify user was saved in the database
        UserEntity savedUser = userRepository.findByUsername("testuser").orElse(null);
        assertNotNull(savedUser); // Check user is not null
        assertTrue(passwordEncoder.matches("password123", savedUser.getPassword())); // Check password is encoded
        assertNotNull(savedUser.getLastLogin()); // Ensure lastLogin is set
    }

    @Test
    public void register_ConflictUsernameEmailPhone_Conflict() throws Exception {
        // Arrange: Create a user and save it in the database to cause a conflict
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("testuser");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        existingUser.setEmail("testuser@email.com");
        existingUser.setPhone("+919660661696");
        existingUser.setRole(Role.USER);
        userRepository.save(existingUser);

        // Create a new user with the same username and email
        UserEntity newUser = new UserEntity();
        newUser.setUsername("testuser");
        newUser.setPassword("newpassword123");
        newUser.setEmail("testuser@email.com");
        newUser.setPhone("+919660661696");
        newUser.setRole(Role.USER);

        // Act: Perform POST request to /register, expecting conflict
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict()) // Assert: Expect conflict (409)
                .andExpect(jsonPath("$.errorMessage").value("CON - Username, email and phone already exists"));
    }

    @Test
    public void register_ConflictUsernameAndEmail_Conflict() throws Exception {
        // Arrange: Create a user and save it in the database to cause a conflict
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("testuser");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        existingUser.setEmail("testuser@email.com");
        existingUser.setRole(Role.USER);
        userRepository.save(existingUser);

        // Create a new user with the same username and email
        UserEntity newUser = new UserEntity();
        newUser.setUsername("testuser");
        newUser.setPassword("newpassword123");
        newUser.setEmail("testuser@email.com");
        newUser.setRole(Role.USER);

        // Act: Perform POST request to /register, expecting conflict
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict()) // Assert: Expect conflict (409)
                .andExpect(jsonPath("$.errorMessage").value("CON - Username and email already exists"));
    }

    @Test
    public void register_ConflictUsernameAndPhone_Conflict() throws Exception {
        // Arrange: Create a user and save it in the database to cause a conflict
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("testuser");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        existingUser.setEmail("testuser@email.com");
        existingUser.setPhone("+919660661696");
        existingUser.setRole(Role.USER);
        userRepository.save(existingUser);

        // Create a new user with the same username and email
        UserEntity newUser = new UserEntity();
        newUser.setUsername("testuser");
        newUser.setPassword("newpassword123");
        newUser.setEmail("testuser2@email.com");
        newUser.setPhone("+919660661696");
        newUser.setRole(Role.USER);

        // Act: Perform POST request to /register, expecting conflict
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict()) // Assert: Expect conflict (409)
                .andExpect(jsonPath("$.errorMessage").value("CON - Username and phone already exists"));
    }

    @Test
    public void register_ConflictEmailAndPhone_Conflict() throws Exception {
        // Arrange: Create a user and save it in the database to cause a conflict
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("testuser");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        existingUser.setEmail("testuser@email.com");
        existingUser.setPhone("+919660661696");
        existingUser.setRole(Role.USER);
        userRepository.save(existingUser);

        // Create a new user with the same username and email
        UserEntity newUser = new UserEntity();
        newUser.setUsername("testuser2");
        newUser.setPassword("newpassword123");
        newUser.setEmail("testuser@email.com");
        newUser.setPhone("+919660661696");
        newUser.setRole(Role.USER);

        // Act: Perform POST request to /register, expecting conflict
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict()) // Assert: Expect conflict (409)
                .andExpect(jsonPath("$.errorMessage").value("CON - Email and phone already exists"));
    }

    @Test
    public void register_ConflictUsername_Conflict() throws Exception {
        // Arrange: Save a user with a specific username in the database
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("testuser");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        existingUser.setEmail("testuser@email.com");
        existingUser.setRole(Role.USER);
        userRepository.save(existingUser);

        // Create a new user with the same username but different email
        UserEntity newUser = new UserEntity();
        newUser.setUsername("testuser");
        newUser.setPassword("newpassword123");
        newUser.setEmail("testuser2@email.com");
        newUser.setRole(Role.USER);

        // Act: Perform POST request to /register, expecting conflict
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict()) // Assert: Expect conflict (409)
                .andExpect(jsonPath("$.errorMessage").value("CON - Username already exists"));
    }

    @Test
    public void register_ConflictEmail_Conflict() throws Exception {
        // Arrange: Save a user with a specific email in the database
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("testuser");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        existingUser.setEmail("testuser@email.com");
        existingUser.setRole(Role.USER);
        userRepository.save(existingUser);

        // Create a new user with a different username but the same email
        UserEntity newUser = new UserEntity();
        newUser.setUsername("testuser2");
        newUser.setPassword("newpassword123");
        newUser.setEmail("testuser@email.com");
        newUser.setRole(Role.USER);

        // Act: Perform POST request to /register, expecting conflict
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict()) // Assert: Expect conflict (409)
                .andExpect(jsonPath("$.errorMessage").value("CON - Email already exists"));
    }

    @Test
    public void register_ConflictPhone_Conflict() throws Exception {
        // Arrange: Save a user with a specific phone in the database
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("testuser");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        existingUser.setEmail("testuser@email.com");
        existingUser.setPhone("+91 9440146632");
        existingUser.setRole(Role.USER);
        userRepository.save(existingUser);

        // Create a new user with a different username but the same phone
        UserEntity newUser = new UserEntity();
        newUser.setUsername("testuser2");
        newUser.setPassword("newpassword123");
        newUser.setEmail("testuser2@email.com");
        newUser.setPhone("+91 9440146632");
        newUser.setRole(Role.USER);

        // Act: Perform POST request to /register, expecting conflict
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict()) // Assert: Expect conflict (409)
                .andExpect(jsonPath("$.errorMessage").value("CON - Phone already exists"));
    }

    @Test
    public void login_ValidCredentials_Success() throws Exception {
        
       // Arrange: Create a user entity object
       UserEntity user = new UserEntity();
       user.setUsername("testuser");
       user.setPassword("password123");
       user.setEmail("testuser@email.com");
       user.setRole(Role.USER);

       mockMvc.perform(post("/register")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(user)));

        // Create a user entity object
        UserEntity sameuser = new UserEntity();
        sameuser.setUsername("testuser");
        sameuser.setPassword("password123");

        // Act: Perform POST request to /login
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sameuser)))
                // Assert: Verify status Ok and whether token and lastLogin are in response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.lastLogin").exists());
    }

    @Test
    public void login_WrongCredentials_BadRequest() throws Exception {
        
       // Arrange: Create a user entity object
       UserEntity user = new UserEntity();
       user.setUsername("testuser");
       user.setPassword("password123");
       user.setEmail("testuser@email.com");
       user.setRole(Role.USER);

       mockMvc.perform(post("/register")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(user)));

        // Create a user entity object
        UserEntity sameuser = new UserEntity();
        sameuser.setUsername("testuser");
        sameuser.setPassword("wrongpassword");

        // Act: Perform POST request to /login
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sameuser)))
                // Assert: Verify status Ok and whether token and lastLogin are in response
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("ILL - Invalid username or password"));
    }
}
