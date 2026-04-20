package com.ume.studentsystem.dto.response;

import java.util.List;

public record TranscriptResponse(

        Long studentId,
        String studentName,
        String studentCode,

        List<TranscriptItemResponse> subjects,

        Integer totalCredits,
        Double semesterGpa,
        Double cgpa,

        Integer passedSubjects,
        Integer failedSubjects
) {
}
