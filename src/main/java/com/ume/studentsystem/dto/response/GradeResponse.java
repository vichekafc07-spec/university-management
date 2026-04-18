package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.GradeStatus;

public record GradeResponse(

        Long id,
        String studentName,
        String subjectTitle,

        Double attendanceScore,
        Double assignmentScore,
        Double midtermScore,
        Double finalScore,

        Double totalScore,
        GradeStatus grade,
        Double gpa
) {}
