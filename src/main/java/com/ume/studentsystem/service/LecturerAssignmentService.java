package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.AssignLecturerRequest;
import com.ume.studentsystem.dto.response.LecturerAssignmentResponse;
import com.ume.studentsystem.util.PageResponse;

import java.util.List;

public interface LecturerAssignmentService {

    LecturerAssignmentResponse assignLecturer(AssignLecturerRequest request);

    PageResponse<LecturerAssignmentResponse> getLecturerAssignments(Long lecturerId, String lecturerName, String lecturerCode, String sortBy, String sortAs, Integer page, Integer size);

    List<LecturerAssignmentResponse> getClassroomAssignments(Long classroomId);

    void removeAssignment(Long id);
}
