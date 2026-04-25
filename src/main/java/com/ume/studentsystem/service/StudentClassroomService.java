package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.AssignStudentClassroomRequest;
import com.ume.studentsystem.dto.request.RemoveStudentClassroomRequest;
import com.ume.studentsystem.dto.response.student.StudentClassroomResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface StudentClassroomService {

    List<StudentClassroomResponse> assignStudent(AssignStudentClassroomRequest request);

    PageResponse<StudentClassroomResponse> getStudentsInClassroom(Long classroomId, String studentCode, String sortBy, String sortAs, Integer page, Integer size);

    List<StudentClassroomResponse> getStudentHistory(Long studentId);

    void promoteClassroom(Long classroomId, Long nextClassroomId);

    void removeStudent(@Valid RemoveStudentClassroomRequest request);
}
