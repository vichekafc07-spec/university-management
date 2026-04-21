package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ExamResultBulkRequest(
        @NotNull Long examId,
        @NotEmpty List<StudentScoreItem> students
) {
}
