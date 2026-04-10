package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record AssignStudentSubjectRequest(
        @NotEmpty(message = "Student is required")
        Set<Long> studentId,

        @NotNull(message = "Classroom is required")
        Long classroomId,

        @NotEmpty(message = "Subjects are required")
        Set<Long> subjectIds
) {}