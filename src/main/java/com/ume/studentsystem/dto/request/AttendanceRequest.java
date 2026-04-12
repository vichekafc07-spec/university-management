package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.AttendanceStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

public record AttendanceRequest(

        @NotEmpty(message = "StudentSubject IDs required")
        Set<Long> studentSubjectIds,

        @NotNull(message = "Session Id is required")
        Long sessionId,

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotNull(message = "Status is required")
        AttendanceStatus status
) {}
