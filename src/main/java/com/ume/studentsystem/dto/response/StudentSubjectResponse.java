package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.StudentStatus;
import java.time.LocalDate;

public record StudentSubjectResponse(
        Long id,
        String studentName,
        String studentCode,

        String subjectTitle,

        String classroomName,
        Integer semester,

        LocalDate enrollDate,
        StudentStatus status
) {}
