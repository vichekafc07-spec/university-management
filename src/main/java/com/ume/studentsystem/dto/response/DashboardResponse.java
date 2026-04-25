package com.ume.studentsystem.dto.response;

public record DashboardResponse(
        Long totalStudents,
        Long paidStudents,
        Long unpaidStudents,
        Double attendancePercent,
        String topFaculty,
        Double revenueThisMonth
) {
}
