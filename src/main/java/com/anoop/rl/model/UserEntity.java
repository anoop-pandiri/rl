package com.anoop.rl.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.anoop.rl.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "USERS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @NotNull(message = "Username cannot be null")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name="password", nullable = false)
    @NotNull(message = "Password cannot be null")
    private String password;

    @Email
    @NotNull(message = "Email cannot be null")
    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", nullable = true)
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phone;

    @NotNull(message = "Role cannot be null")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public Role getRole(){
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "last_login", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp lastLogin;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    // Getters and setters
}
