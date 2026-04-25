package com.ume.studentsystem.dto.request.exam;

import com.ume.studentsystem.dto.request.student.StudentScoreItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ExamResultBulkRequest(
        @NotNull
        Long examId,
        @NotEmpty
        List<StudentScoreItem> students
) {
}
