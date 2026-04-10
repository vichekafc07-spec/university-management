package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.StudyTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ClassroomRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Year is required")
        Integer year,

        @NotNull(message = "Semester is required")
        Integer semester,

        @NotNull(message = "Generation is required")
        Integer generation,

        @NotNull(message = "Time is required")
        StudyTime time,

        @NotNull(message = "Department ID required")
        Integer departmentId,

        Set<Long> subjectIds
) {}
