package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record AssignSubjectRequest(
        @NotNull(message = "Subject id is required")
        Set<Long> subjectIds
) {
}
