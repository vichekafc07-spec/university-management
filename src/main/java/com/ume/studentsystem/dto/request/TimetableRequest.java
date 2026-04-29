package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record TimetableRequest(

        @NotNull(message = "Term is required")
        Long termId,

        @NotNull(message = "Classroom is required")
        Long classroomId,

        @NotNull(message = "Subject is required")
        Long subjectId,

        @NotNull(message = "Lecturer is required")
        Long lecturerId,

        @NotNull(message = "Room is required")
        Integer roomId,

        @NotNull(message = "Day is required")
        DayOfWeek dayOfWeek,

        @NotNull(message = "Start time is required")
        LocalTime startTime,

        @NotNull(message = "End time is required")
        LocalTime endTime
) {
}
