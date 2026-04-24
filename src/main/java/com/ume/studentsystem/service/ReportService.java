package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.response.FacultyTopStudentResponse;
import com.ume.studentsystem.dto.response.RankingResponse;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ReportService {
    ByteArrayInputStream classroomStudentList(Long classroomId);
    ByteArrayInputStream exportStudentsExcel(Long classroomId);
    ByteArrayInputStream attendanceSheet(Long sessionId);
    ByteArrayInputStream generateSeatList(Long examId);
    ByteArrayInputStream generateTranscript(Long studentId);
    List<RankingResponse> rankByClassroom(Long classroomId);
    List<FacultyTopStudentResponse> getTopStudentsByFaculty(Long facultyId);
    ByteArrayInputStream generateReceipt(Long paymentId);
}
