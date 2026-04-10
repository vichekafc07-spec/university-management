package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.StudyTime;
import jakarta.validation.constraints.NotNull;

public record AssignLecturerRequest(
        @NotNull(message = "Lecturer is required")
        Long lecturerId,

        @NotNull(message = "Classroom is required")
        Long classroomId,

        @NotNull(message = "Subject is required")
        Long subjectId,

        @NotNull(message = "Study time is required")
        StudyTime time
) {}
