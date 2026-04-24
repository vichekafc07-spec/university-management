package com.ume.studentsystem.dto.response;

public record RankingResponse(
        Long studentId,
        String studentName,
        String studentCode,
        Double gpa,
        Integer rank
) {
}
