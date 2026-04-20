package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.GradeStatus;

public record TranscriptItemResponse(

        String subjectCode,
        String subjectTitle,
        Integer credit,

        Double totalScore,
        GradeStatus grade,
        Double gpa
) {
}
