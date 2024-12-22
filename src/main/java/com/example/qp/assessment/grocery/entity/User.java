package com.example.qp.assessment.grocery.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String password;

    //@ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Role role;

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(UUID id, Role role, String password, String username) {
        this.id = id;
        this.role = role;
        this.password = password;
        this.username = username;
    }

    public User() {
    }
}
