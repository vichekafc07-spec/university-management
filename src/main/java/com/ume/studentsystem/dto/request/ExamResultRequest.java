package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record ExamResultRequest(

        @NotNull(message = "Exam is required")
        Long examId,

        @NotNull(message = "StudentSubject is required")
        Long studentSubjectId,

        @NotNull(message = "Score is required")
        @DecimalMin(value = "0")
        Double score,

        String remark
) {
}
