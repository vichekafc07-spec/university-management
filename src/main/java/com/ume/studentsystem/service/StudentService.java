package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.StudentRequest;
import com.ume.studentsystem.dto.response.StudentResponse;
import com.ume.studentsystem.util.PageResponse;

public interface StudentService {

    StudentResponse create(StudentRequest request);

    StudentResponse update(Long id, StudentRequest request);

    StudentResponse getStudentById(Long id);

    PageResponse<StudentResponse> getAll(Long id, String fullName, String studentCode, String faculty, String major, Integer generation, String payment, String programType, String status, String sortBy, String sortAs, Integer page, Integer size);

    void delete(Long id);
}
