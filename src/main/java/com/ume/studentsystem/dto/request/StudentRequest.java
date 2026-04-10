package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.GenderStatus;
import com.ume.studentsystem.model.enums.PaymentType;
import com.ume.studentsystem.model.enums.ProgramType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record StudentRequest(
        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Student code is required")
        String studentCode,

        @NotNull(message = "Date of birth is required")
        LocalDate dateOfBirth,

        @NotBlank(message = "Phone is required")
        @Size(min = 8 , max = 20)
        String phone,

        @NotBlank(message = "Study time is required")
        String studyTime,

        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Generate is required")
        Integer generation,

        @NotNull(message = "Gender is required")
        GenderStatus gender,

        @NotNull(message = "Program type is required")
        ProgramType programType,

        @NotNull(message = "Payment type is required")
        PaymentType paymentType,

        @NotNull(message = "Faculty ID is required")
        Byte facultyId,

        @NotNull(message = "Department ID is required")
        Integer departmentId,

        @NotBlank(message = "Major is required")
        String major,

        @NotBlank(message = "Enrollment year is required")
        String enrollmentYear,

        @NotNull(message = "Status is required")
        String status
) {}
