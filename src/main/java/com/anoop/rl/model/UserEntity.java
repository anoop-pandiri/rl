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

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "USERS")
public class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name="password",nullable = false)
    private String password;

    @Email
    @Column(name="email")
    private String email;

    @Column(name = "phone", unique = true, nullable = true)
    private Integer phone;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
