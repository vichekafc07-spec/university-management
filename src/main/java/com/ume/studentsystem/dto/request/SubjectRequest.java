package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubjectRequest(
        @NotBlank(message = "Code must not blank")
        String code,
        @NotBlank(message = "Title must not blank")
        String title,
        @NotNull(message = "Credit must not null")
        int credit,
        @NotNull(message = "Department id required")
        Integer departmentId
) {
}
