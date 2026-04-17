package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotNull;

public record GradeRequest(

        @NotNull(message = "StudentSubject is required")
        Long studentSubjectId,

        @NotNull
        Double assignmentScore,

        @NotNull
        Double midtermScore,

        @NotNull
        Double finalScore
) {}
