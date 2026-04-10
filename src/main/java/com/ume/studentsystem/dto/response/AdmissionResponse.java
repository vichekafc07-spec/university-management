package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.StudentStatus;

public record AdmissionResponse(
        Long id,
        String studentName,
        String studentCode,
        StudentStatus status
) { }
