package com.holidaybookingproject.HolidayHomeBooking.dto;

import jakarta.validation.constraints.*;
import lombok.*;


public class UpdateProfileRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @Email
    private String email;

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
}


