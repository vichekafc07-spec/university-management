package com.ume.studentsystem.dto.response;

public record SubjectResponse(
        Long id,
        String code,
        String title,
        int credit,
        String department
) {
}
