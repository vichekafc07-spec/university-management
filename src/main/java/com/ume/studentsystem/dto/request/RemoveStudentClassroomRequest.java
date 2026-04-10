package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RemoveStudentClassroomRequest(
        @NotEmpty(message = "Students are required")
        Set<Long> studentIds,
        @NotNull(message = "Classroom is required")
        Long classroomId
) {
}
