package com.ume.studentsystem.service;

import java.io.ByteArrayInputStream;

public interface ReportService {
    ByteArrayInputStream classroomStudentList(Long classroomId);
    ByteArrayInputStream exportStudentsExcel(Long classroomId);
    ByteArrayInputStream attendanceSheet(Long sessionId);
    ByteArrayInputStream generateSeatList(Long examId);
}
