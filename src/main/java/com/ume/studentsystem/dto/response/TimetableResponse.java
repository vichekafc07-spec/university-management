package com.ume.studentsystem.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record TimetableResponse(
        Long id,
        Integer year,
        Integer semester,

        String classroomName,
        String subjectTitle,
        String lecturerName,
        String roomName,

        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
) {
}
