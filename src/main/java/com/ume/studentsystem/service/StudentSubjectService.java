package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.AssignStudentSubjectRequest;
import com.ume.studentsystem.dto.request.UpdateStudentStatus;
import com.ume.studentsystem.dto.response.StudentSubjectResponse;
import com.ume.studentsystem.util.PageResponse;

import java.util.List;

public interface StudentSubjectService {
    List<StudentSubjectResponse> assignSubjects(AssignStudentSubjectRequest request);

    PageResponse<StudentSubjectResponse> getSubjectsByStudent(Long studentId, String studentName, String studentCode, Integer semester, String sortBy, String sortAs, Integer page, Integer size);

    PageResponse<StudentSubjectResponse> getStudentBySubject(Long subjectId,Integer semester, String sortBy, String sortAs, Integer page, Integer size);

    StudentSubjectResponse updateStatus(Long id, UpdateStudentStatus status);

    void deleteStudentSubject(Long id);
}
