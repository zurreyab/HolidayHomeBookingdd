package com.holidaybookingproject.HolidayHomeBooking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ChangePasswordRequest {
    @NotBlank private String oldPassword;
    @NotBlank private String newPassword;
}
