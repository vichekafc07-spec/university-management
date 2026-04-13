package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record SessionRequest(

        @NotNull(message = "Lecturer Assignment is required")
        Long lecturerAssignmentId,

        @NotNull(message = "Room is required")
        Long roomId,

        @NotNull(message = "Day is required")
        DayOfWeek day,

        @NotNull(message = "Start time is required")
        LocalTime startTime,

        @NotNull(message = "End time is required")
        LocalTime endTime
) {}
