package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.GenderStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record StaffUpdateRequest(
        @NotBlank(message = "FullName is required")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String personalEmail,

        @NotBlank(message = "Staff Code is required")
        String staffCode,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^[0-9+]{8,15}$", message = "Invalid phone number")
        String phone,

        @NotNull(message = "Gender is required")
        GenderStatus gender,

        @NotNull(message = "Date of birth id is required")
        LocalDate dob,

        @NotNull(message = "Faculty id is required")
        Byte facultyId,

        @NotNull(message = "Department id is required")
        Integer departmentId
) {}
