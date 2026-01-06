package com.holidaybookingproject.HolidayHomeBooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity

@Table(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Column(unique = true)
    @NotBlank
    private String username;

    @Email
    @Column(unique = true)
    @NotBlank
    private String email;

    @NotBlank
    private
    String passwordHash;
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();     // JPA
    public User(String name, String username,
                String email, String passwordHash ,String address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.address=address
        ;


    }

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(Long id, String name, String username, String email, int penaltyPoints, String passwordHash, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roles = roles;
    }
    // getters & setters …

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
