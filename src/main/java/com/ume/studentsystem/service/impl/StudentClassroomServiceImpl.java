package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.AssignStudentClassroomRequest;
import com.ume.studentsystem.dto.request.RemoveStudentClassroomRequest;
import com.ume.studentsystem.dto.response.StudentClassroomResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.StudentClassroomMapper;
import com.ume.studentsystem.model.Classroom;
import com.ume.studentsystem.model.Student;
import com.ume.studentsystem.model.StudentClassroom;
import com.ume.studentsystem.model.enums.StudentStatus;
import com.ume.studentsystem.repository.ClassroomRepository;
import com.ume.studentsystem.repository.StudentClassroomRepository;
import com.ume.studentsystem.repository.StudentRepository;
import com.ume.studentsystem.service.StudentClassroomService;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentClassroomServiceImpl implements StudentClassroomService {

    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;
    private final StudentClassroomRepository studentClassroomRepository;
    private final StudentClassroomMapper scMapper;

    @Override
    @Transactional
    public List<StudentClassroomResponse> assignStudent(AssignStudentClassroomRequest request) {

        var classroom = getById(request.classroomId());
        var students = new HashSet<>(studentRepository.findAllById(request.studentIds()));
        if (students.isEmpty()){
            throw new BadRequestException("No valid student found");
        }

        List<StudentClassroomResponse> responses = new ArrayList<>();
        for (var student : students){
            if (studentStatus(student)){
                throw new BadRequestException("student can't be assigned");
            }
            if (studentClassroomRepository.existsByStudentIdAndClassroomId(student.getId(), classroom.getId())) {
                throw new BadRequestException("Student with ID " + student.getId() + " is already assigned to this classroom");
            }

            var sc = new StudentClassroom();
            sc.setStudent(student);
            sc.setClassroom(classroom);
            sc.setYear(classroom.getYear());
            sc.setSemester(classroom.getSemester());
            sc.setGeneration(classroom.getGeneration());
            sc.setStatus(StudentStatus.ACTIVE);
            sc.setAssignedDate(LocalDate.now());
            responses.add(scMapper.toResponse(studentClassroomRepository.save(sc)));
        }

        return responses;
    }

    @Override
    public void removeStudent(RemoveStudentClassroomRequest request) {

        var classroom = getById(request.classroomId());

        var studentClassrooms = new HashSet<>(studentClassroomRepository.findAllById(request.studentIds()));

        if (studentClassrooms.isEmpty()) {
            throw new ResourceNotFoundException("No student assignments found for the given IDs in classroom " + classroom.getId());
        }

        studentClassroomRepository.deleteAll(studentClassrooms);
    }

    @Override
    public PageResponse<StudentClassroomResponse> getStudentsInClassroom(Long classroomId, String studentCode, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<StudentClassroom> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (classroomId != null){
                predicates.add(cb.equal(root.join("classroom").get("id"), classroomId));
            }
            if (studentCode != null) {
                predicates.add(cb.like(root.join("student").get("studentCode"), "%" + studentCode + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<String> allowSort = List.of("student.studentCode","classroom.id");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page-1 , size , sort);
        Page<StudentClassroom> scPage = studentClassroomRepository.findAll(spec,pageable);
        return PageResponse.from(scPage,scMapper::toResponse);
    }

    @Override
    public List<StudentClassroomResponse> getStudentHistory(Long studentId) {
        return studentClassroomRepository.findByStudentId(studentId)
                .stream()
                .map(scMapper::toResponse)
                .toList();
    }

    @Override
    public void promoteClassroom(Long classroomId, Long nextClassroomId) {

        var nextClassroom = getById(nextClassroomId);

        var students = studentClassroomRepository.findByClassroomId(classroomId);

        List<StudentClassroom> promotedList = students.stream()
                .map(sc -> StudentClassroom.builder()
                        .student(sc.getStudent())
                        .classroom(nextClassroom)
                        .year(nextClassroom.getYear())
                        .semester(nextClassroom.getSemester())
                        .generation(nextClassroom.getGeneration())
                        .assignedDate(LocalDate.now())
                        .status(StudentStatus.ACTIVE)
                        .build())
                .toList();
        studentClassroomRepository.saveAll(promotedList);
    }

    private boolean studentStatus(Student student) {
        return student.getStatus() == StudentStatus.REJECTED ||
                student.getStatus() == StudentStatus.SUSPENSION ||
                student.getStatus() == StudentStatus.GRADUATE ||
                student.getStatus() == StudentStatus.PENDING;
    }

    private Classroom getById(Long id){
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found " + id));
    }
}
