package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.DepartmentRequest;
import com.ume.studentsystem.dto.response.DepartmentResponse;
import com.ume.studentsystem.model.Department;
import com.ume.studentsystem.util.PageResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse addDepartment(DepartmentRequest request);
    PageResponse<DepartmentResponse> getAllDepartment(Integer id,String name,String sortBy,String sortAs,Integer page,Integer size);
    List<DepartmentResponse> getDepartmentByFaculty(Byte facultyId);
    DepartmentResponse updateDepartment(Integer id, DepartmentRequest request);
    void deleteDepartment(Integer id);
    String restoreDept(Integer id);
}
