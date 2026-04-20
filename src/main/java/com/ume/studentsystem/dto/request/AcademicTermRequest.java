package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.TermStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AcademicTermRequest(
        @NotNull(message = "year is required")
        Integer year,

        @NotNull(message = "semester is required")
        Integer semester,

        @NotNull(message = "start date is required")
        LocalDate startDate,

        @NotNull(message = "end date is required")
        LocalDate endDate,

        @NotNull(message = "status is required")
        TermStatus status
) {
}
