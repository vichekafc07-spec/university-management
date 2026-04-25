package com.ume.studentsystem.dto.request.student;

import com.ume.studentsystem.model.enums.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public record StudentRequest(

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Student code is required")
        String studentCode,

        @NotNull(message = "Gender is required")
        GenderStatus gender,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @NotNull(message = "Date of birth is required")
        LocalDate dateOfBirth,

        @NotBlank(message = "Phone is required")
        String phone,

        @Email(message = "Invalid email")
        String email,

        @NotNull
        ProgramType programType,

        @NotNull
        PaymentType paymentType,

        @NotNull
        Byte facultyId,

        @NotNull
        Integer departmentId,

        @NotBlank
        String major,

        @NotBlank
        String enrollmentYear,

        @NotNull
        Integer generation,

        @NotNull
        StudyTime studyTime,

        @NotNull
        StudentStatus status
) {}