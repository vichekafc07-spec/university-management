package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.DepartmentRequest;
import com.ume.studentsystem.dto.response.DepartmentResponse;
import com.ume.studentsystem.exceptions.DuplicateResourceException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.DepartmentMapper;
import com.ume.studentsystem.model.Department;
import com.ume.studentsystem.model.Faculty;
import com.ume.studentsystem.repository.DepartmentRepository;
import com.ume.studentsystem.repository.FacultyRepository;
import com.ume.studentsystem.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponse addDepartment(DepartmentRequest request) {
        departmentRepository.findDepartmentByName(request.name()).ifPresent(e -> {
            throw new DuplicateResourceException("Department already exists with name: " + request.name());
        });
        var faculty = getFacultyId(request.facultyId());
        var dept = departmentMapper.toEntity(request);
        dept.setFaculty(faculty);
        var saved = departmentRepository.save(dept);
        return departmentMapper.toResponse(saved);
    }

    @Override
    public List<DepartmentResponse> getAllDepartment() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<DepartmentResponse> getDepartmentByFaculty(Byte facultyId) {
        if (!facultyRepository.existsById(facultyId)){
            throw new ResourceNotFoundException("Faculty not found with id " + facultyId);
        }
        return departmentRepository.findByFacultyId(facultyId)
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    public DepartmentResponse updateDepartment(Integer id, DepartmentRequest request) {
        var department = getDepartmentId(id);
        var faculty = getFacultyId(request.facultyId());
        department.setFaculty(faculty);
        departmentMapper.toUpdate(request,department);
        var saved = departmentRepository.save(department);

        return departmentMapper.toResponse(saved);
    }

    @Override
    public void deleteDepartment(Integer id) {
        var department = getDepartmentId(id);
        departmentRepository.delete(department);
    }

    private Faculty getFacultyId(Byte facultyId){
        return facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + facultyId));
    }

    private Department getDepartmentId(Integer id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

}