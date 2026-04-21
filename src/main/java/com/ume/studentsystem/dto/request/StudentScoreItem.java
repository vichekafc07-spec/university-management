package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record StudentScoreItem(
        @NotNull
        Long studentSubjectId,
        @NotNull
        @DecimalMin("0") Double score,
        String remark
) {
}
