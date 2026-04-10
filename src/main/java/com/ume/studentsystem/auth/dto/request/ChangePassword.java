package com.ume.studentsystem.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePassword(
        String oldPassword,
        @NotBlank(message = "Password is required")
        @Size(max = 25, min = 6, message = "Password must between 6 to 25 characters")
        String newPassword
) {
}