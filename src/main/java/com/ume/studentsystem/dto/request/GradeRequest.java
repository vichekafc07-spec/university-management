package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record GradeRequest(

        @NotNull(message = "StudentSubject is required")
        Long studentSubjectId,

        @DecimalMin("0.0")
        @NotNull
        Double assignmentScore,

        @DecimalMin("0.0")
        @NotNull
        Double midtermScore,

        @DecimalMin("0.0")
        @NotNull
        Double finalScore
) {}
