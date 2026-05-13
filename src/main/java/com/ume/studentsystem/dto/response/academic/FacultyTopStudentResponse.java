package com.ume.studentsystem.dto.response.academic;

public record FacultyTopStudentResponse(
        Long studentId,
        String studentName,
        String studentCode,
        Double gpa,
        Integer rank
) {
}
