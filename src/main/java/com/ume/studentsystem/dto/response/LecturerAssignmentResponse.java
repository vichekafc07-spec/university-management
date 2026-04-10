package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.StudyTime;
import java.time.LocalDate;

public record LecturerAssignmentResponse(
        Long id,
        String lecturerName,
        String lecturerCode,
        String classroomName,
        String subjectName,
        StudyTime time,
        LocalDate assignedDate
) {}
