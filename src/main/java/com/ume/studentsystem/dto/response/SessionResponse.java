package com.ume.studentsystem.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record SessionResponse(
        Long id,

        String lecturerName,
        String subjectName,
        String classroomName,

        String roomName,

        DayOfWeek day,
        LocalTime startTime,
        LocalTime endTime
) {}
