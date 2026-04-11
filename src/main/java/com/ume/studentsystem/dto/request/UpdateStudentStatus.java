package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.StudentStatus;

public record UpdateStudentStatus(
        StudentStatus status
) {
}
