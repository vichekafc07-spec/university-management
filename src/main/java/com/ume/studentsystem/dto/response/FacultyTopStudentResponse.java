package com.ume.studentsystem.dto.response;

public record FacultyTopStudentResponse(
        Long studentId,
        String studentName,
        String studentCode,
        Double gpa,
        Integer rank
) {
}
