package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.SubjectRequest;
import com.ume.studentsystem.dto.response.SubjectResponse;
import com.ume.studentsystem.exceptions.DuplicateResourceException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.model.Department;
import com.ume.studentsystem.spec.SpecificationBuilder;
import com.ume.studentsystem.mapper.SubjectMapper;
import com.ume.studentsystem.model.Subject;
import com.ume.studentsystem.repository.DepartmentRepository;
import com.ume.studentsystem.repository.SubjectRepository;
import com.ume.studentsystem.service.SubjectService;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public SubjectResponse create(SubjectRequest request) {
        var department = getDepartmentId(request.departmentId());
        if (subjectRepository.existsByCode((request.code()))) {
            throw new DuplicateResourceException("Course code already exists");
        }
        var subject = subjectMapper.toEntity(request);
        subject.setDepartment(department);
        var saved = subjectRepository.save(subject);
        return subjectMapper.toResponse(saved);
    }

    @Override
    public SubjectResponse update(Long id, SubjectRequest request) {
        var subject = getById(id);
        var department = getDepartmentId(request.departmentId());
        subjectMapper.update(request,subject);
        subject.setDepartment(department);
        return subjectMapper.toResponse(subjectRepository.save(subject));
    }

    @Override
    public void delete(Long id) {
        var subject = getById(id);
        subjectRepository.delete(subject);
    }

    @Override
    public PageResponse<SubjectResponse> getAllStaff(Long id, String code, String title, String department, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Subject> spec = new SpecificationBuilder<Subject>()
                .equal("id", id)
                .like("code", code)
                .like("title", title)
                .like("department", department)
                .build();

        List<String> allowSort = List.of("id","code","title","department");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1,size,sort);
        Page<Subject> subjectPage = subjectRepository.findAll(spec,pageable);
        return PageResponse.from(subjectPage,subjectMapper::toResponse);
    }

    private Subject getById(Long id){
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
    }

    private Department getDepartmentId(Integer id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }
}
