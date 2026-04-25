package com.ume.studentsystem.dto.response.exam;

import com.ume.studentsystem.model.enums.ExamType;
import java.time.LocalDate;

public record ExamResultResponse(

        Long id,

        String studentName,
        String studentCode,

        String subjectTitle,
        ExamType examType,

        LocalDate examDate,

        Double score,
        String remark
) {
}
