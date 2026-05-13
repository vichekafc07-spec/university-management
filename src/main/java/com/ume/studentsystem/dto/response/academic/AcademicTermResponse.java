package com.ume.studentsystem.dto.response.academic;

import com.ume.studentsystem.model.enums.TermStatus;
import java.time.LocalDate;

public record AcademicTermResponse(
        Long id,
        Integer year,
        Integer semester,
        LocalDate startDate,
        LocalDate endDate,
        TermStatus status
) {
}
