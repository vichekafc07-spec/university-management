package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.GenderStatus;
import com.ume.studentsystem.model.enums.StaffPosition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record StaffRequest(
        @NotBlank(message = "FullName is required")
        String fullName,
        @NotNull(message = "Position is required")
        StaffPosition position,
        @NotBlank(message = "Email is required")
        String personalEmail,
        @NotBlank(message = "Phone is required")
        String phone,
        @NotNull(message = "Gender is required")
        GenderStatus gender,
        @NotNull(message = "Dob is required")
        LocalDate dob,
        boolean active,
        @NotNull(message = "Faculty Id is required")
        Byte facultyId,
        @NotNull(message = "Department Id is required")
        Integer departmentId,
        @NotNull(message = "User Id is required")
        Long userId
) {}
