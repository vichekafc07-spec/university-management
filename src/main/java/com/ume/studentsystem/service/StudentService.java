package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.StudentRequest;
import com.ume.studentsystem.dto.response.StudentResponse;
import com.ume.studentsystem.util.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface StudentService {

    StudentResponse create(StudentRequest request, MultipartFile photo);

    StudentResponse update(Long id, StudentRequest request);

    StudentResponse getStudentById(Long id);

    PageResponse<StudentResponse> getAll(Long id, String fullName, String studentCode, String faculty, String major, Integer generation, String payment, String programType, String status, String sortBy, String sortAs, Integer page, Integer size);

    void delete(Long id);

    ByteArrayInputStream exportByGeneration(Integer generation);

}
