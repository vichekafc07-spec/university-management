package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.AttendanceRequest;
import com.ume.studentsystem.dto.request.UpdateAttendanceStatus;
import com.ume.studentsystem.dto.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {

    List<AttendanceResponse> markAttendance(AttendanceRequest request);

    List<AttendanceResponse> getByStudent(Long studentId);

    List<AttendanceResponse> getBySubject(Long subjectId);

    void delete(Long id);

    AttendanceResponse updateAttendanceStatus(Long id, UpdateAttendanceStatus status);
}
