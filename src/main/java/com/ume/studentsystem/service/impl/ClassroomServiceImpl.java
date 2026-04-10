package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.AssignSubjectRequest;
import com.ume.studentsystem.dto.request.ClassroomRequest;
import com.ume.studentsystem.dto.response.ClassroomResponse;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.model.Department;
import com.ume.studentsystem.spec.SpecificationBuilder;
import com.ume.studentsystem.mapper.ClassroomMapper;
import com.ume.studentsystem.model.Classroom;
import com.ume.studentsystem.model.Subject;
import com.ume.studentsystem.repository.ClassroomRepository;
import com.ume.studentsystem.repository.DepartmentRepository;
import com.ume.studentsystem.repository.SubjectRepository;
import com.ume.studentsystem.service.ClassroomService;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;
    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public ClassroomResponse create(ClassroomRequest request) {

        var department = getDepartmentId(request.departmentId());
        var classroom = classroomMapper.toEntity(request);
        classroom.setDepartment(department);

        if (request.subjectIds() != null) {
            Set<Subject> subjects = new HashSet<>(subjectRepository.findAllById(request.subjectIds()));
            classroom.setSubjects(subjects);
        }

        var saved = classroomRepository.save(classroom);
        return classroomMapper.toResponse(saved);
    }

    @Override
    public ClassroomResponse addSubject(Long classroomId, AssignSubjectRequest request) {
        var classroom = getById(classroomId);
        Set<Subject> subjects = new HashSet<>(subjectRepository.findAllById(request.subjectIds()));
        classroom.getSubjects().addAll(subjects);
        return classroomMapper.toResponse(classroomRepository.save(classroom));
    }

    @Override
    public ClassroomResponse removeSubject(Long classroomId, AssignSubjectRequest request) {
        var classroom = getById(classroomId);
        classroom.getSubjects().removeIf(s -> request.subjectIds().contains(s.getId()));
        return classroomMapper.toResponse(classroomRepository.save(classroom));
    }

    @Override
    public ClassroomResponse updateClassroom(Long id, ClassroomRequest request) {
        var classroom = getById(id);
        var department = getDepartmentId(request.departmentId());
        classroom.setDepartment(department);
        classroomMapper.toUpdate(request,classroom);
        var saved = classroomRepository.save(classroom);
        return classroomMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        var classroom = getById(id);
        classroomRepository.delete(classroom);
    }

    @Override
    public String restore(Long id) {
        var classroom = classroomRepository.findByIdIncludeDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("classroom not found " + id));
        classroom.setDeleted(false);
        classroom.setCreatedAt(null);
        classroomRepository.save(classroom);
        return "Classroom restored successfully";
    }

    @Override
    public PageResponse<ClassroomResponse> getAllClassroom(Long id, String name, Integer year, Integer semester, Integer generation, String time, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Classroom> spec = new SpecificationBuilder<Classroom>()
                .equal("id", id)
                .equal("year",year)
                .equal("semester",semester)
                .equal("generation",generation)
                .like("time",time)
                .like("name", name)
                .build();
        List<String> allowSort = List.of("id","name","year","semester","generation","time");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1,size,sort);
        Page<Classroom> classroomPage = classroomRepository.findAll(spec,pageable);
        return PageResponse.from(classroomPage, classroomMapper::toResponse);
    }

    private Classroom getById(Long id){
        return classroomRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id " + id));
    }

    private Department getDepartmentId(Integer id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));

    }
}
