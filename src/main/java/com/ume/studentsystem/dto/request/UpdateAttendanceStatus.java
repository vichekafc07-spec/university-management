package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.AttendanceStatus;

public record UpdateAttendanceStatus(
        AttendanceStatus status
) {
}
