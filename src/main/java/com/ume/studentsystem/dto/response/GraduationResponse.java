package com.ume.studentsystem.dto.response;

import java.util.List;

public record GraduationResponse(

        Long studentId,
        String studentName,
        String studentCode,

        Integer totalCredits,
        Double cgpa,
        Integer failedSubjects,

        boolean eligible,
        List<String> reasons
) {
}
