package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.StudentRequest;
import com.ume.studentsystem.dto.response.StudentResponse;
import com.ume.studentsystem.exceptions.DuplicateResourceException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.model.Department;
import com.ume.studentsystem.model.Faculty;
import com.ume.studentsystem.spec.SpecificationBuilder;
import com.ume.studentsystem.mapper.StudentMapper;
import com.ume.studentsystem.model.Student;
import com.ume.studentsystem.repository.DepartmentRepository;
import com.ume.studentsystem.repository.FacultyRepository;
import com.ume.studentsystem.repository.StudentRepository;
import com.ume.studentsystem.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponse create(StudentRequest request) {
        var faculty = getFacultyId(request.facultyId());
        var department = getDepartmentId(request.departmentId());

        if (studentRepository.existsByFullName(request.fullName())){
            throw new DuplicateResourceException("this is name already exists");
        }

        var student = studentMapper.toEntity(request);
        student.setFaculty(faculty);
        student.setDepartment(department);

        var saved = studentRepository.save(student);
        return studentMapper.toResponse(saved);
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {
        var student = getById(id);
        var faculty = getFacultyId(request.facultyId());
        var department = getDepartmentId(request.departmentId());
        studentMapper.updateStudent(request,student);
        student.setFaculty(faculty);
        student.setDepartment(department);
        var saved = studentRepository.save(student);
        return studentMapper.toResponse(saved);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        var student = getById(id);
        return studentMapper.toResponse(student);
    }

    @Override
    public PageResponse<StudentResponse> getAll(Long id, String fullName, String studentCode, String faculty, String major, Integer generation, String payment, String programType, String status, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Student> spec = new SpecificationBuilder<Student>()
                .equal("id", id)
                .like("fullName", fullName)
                .like("studentCode", studentCode)
                .like("faculty", faculty)
                .like("major", major)
                .equal("generation", generation)
                .like("paymentType", payment)
                .like("programType", programType)
                .like("status", status)
                .build();
        List<String> allowSort = List.of("id", "fullName", "studentCode", "faculty", "major", "generation", "payment", "programType", "status");
        var sort = SortResponse.sortResponse(sortBy, sortAs, allowSort);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Student> studentPage = studentRepository.findAll(spec, pageable);
        return PageResponse.from(studentPage, studentMapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        var student = getById(id);
        studentRepository.delete(student);
    }

    private Student getById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    private Faculty getFacultyId(Byte id){
        return facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + id ));
    }

    private Department getDepartmentId(Integer id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

}
