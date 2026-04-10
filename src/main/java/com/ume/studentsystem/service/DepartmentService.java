package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.DepartmentRequest;
import com.ume.studentsystem.dto.response.DepartmentResponse;
import com.ume.studentsystem.model.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse addDepartment(DepartmentRequest request);
    List<DepartmentResponse> getAllDepartment();
    List<DepartmentResponse> getDepartmentByFaculty(Byte facultyId);
    DepartmentResponse updateDepartment(Integer id, DepartmentRequest request);
    void deleteDepartment(Integer id);
}
