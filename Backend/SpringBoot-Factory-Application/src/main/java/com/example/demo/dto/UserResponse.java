package com.example.demo.dto;

import com.example.demo.entity.Role;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private boolean active;

    public UserResponse() {
    }

    public UserResponse(
            Long id,
            String username,
            String email,
            Role role,
            boolean active) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}