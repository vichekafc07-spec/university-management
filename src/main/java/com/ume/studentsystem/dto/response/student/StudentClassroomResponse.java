package com.ume.studentsystem.dto.response.student;

import com.ume.studentsystem.model.enums.StudentStatus;
import com.ume.studentsystem.model.enums.StudyTime;

import java.time.LocalDate;

public record StudentClassroomResponse(
        Long id,
        String studentName,
        String studentCode,

        String classroomName,
        Integer year,
        Integer semester,
        StudyTime time,
        Integer generation,
        StudentStatus status,
        LocalDate assignedDate
) {}
