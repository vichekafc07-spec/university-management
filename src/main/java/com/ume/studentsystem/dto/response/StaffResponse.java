package com.ume.studentsystem.dto.response;

import java.time.LocalDate;

public record StaffResponse(
        Long id,
        String fullName,
        String position,
        String personalEmail,
        String faculty,
        String department,
        String email,
        String phone,
        String gender,
        LocalDate dob,
        boolean active,
        String staffCode) {}
