package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.StaffRequest;
import com.ume.studentsystem.dto.request.StaffUpdateRequest;
import com.ume.studentsystem.dto.response.StaffResponse;
import com.ume.studentsystem.util.PageResponse;

import java.util.List;

public interface StaffService {
    StaffResponse addStaff(StaffRequest request);
    StaffResponse updateStaff(StaffUpdateRequest request, Long id);
    String deactivate(Long id);
    String activate(Long id);
    PageResponse<StaffResponse> getAllStaff(Long id, String fullName, String position, String faculty, Boolean active, String sortBy, String sortAs, Integer page, Integer size);
    List<StaffResponse> getByDepartment(Integer departmentId);
    List<StaffResponse> getByFaculty(Byte facultyId);
    List<StaffResponse> getLecturers();

    void delete(Long id);

    String restore(Long id);
}
