package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.GenderStatus;
import com.ume.studentsystem.model.enums.PaymentType;
import com.ume.studentsystem.model.enums.ProgramType;

import java.time.LocalDate;

public record StudentResponse(
        Long id,
        String studentCode,
        String fullName,
        GenderStatus gender,
        String phone,
        String studyTime,
        String email,
        LocalDate dateOfBirth,
        ProgramType programType,
        PaymentType paymentType,
        String facultyName,
        String departmentName,
        String major,
        String enrollmentYear,
        Integer generation,
        String status
) {}
