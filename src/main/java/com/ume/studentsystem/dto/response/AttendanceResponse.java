package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.AttendanceStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponse(
        Long id,
        String studentName,
        String subjectTitle,
        String classroomName,
        String lecturerName,
        String room,
        DayOfWeek day,
        LocalTime startTime,
        LocalTime endTime,
        LocalDate date,
        AttendanceStatus status
) {}
