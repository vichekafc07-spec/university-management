package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepartmentRequest(
        @NotBlank(message = "Department name must not blank")
        String name ,
        @NotNull(message = "Faculty Id must not null")
        Byte facultyId) {
}
