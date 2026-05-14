package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.AssignLecturerRequest;
import com.ume.studentsystem.dto.response.LecturerAssignmentResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.LecturerMapper;
import com.ume.studentsystem.model.Classroom;
import com.ume.studentsystem.model.LecturerAssignment;
import com.ume.studentsystem.model.Staff;
import com.ume.studentsystem.model.Subject;
import com.ume.studentsystem.repository.ClassroomRepository;
import com.ume.studentsystem.repository.LecturerAssignmentRepository;
import com.ume.studentsystem.repository.StaffRepository;
import com.ume.studentsystem.repository.SubjectRepository;
import com.ume.studentsystem.service.LecturerAssignmentService;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LecturerAssignmentServiceImpl implements LecturerAssignmentService {

    private final LecturerAssignmentRepository lecturerRepository;
    private final StaffRepository staffRepository;
    private final ClassroomRepository classroomRepository;
    private final SubjectRepository subjectRepository;
    private final LecturerMapper lecturerMapper;

    @Override
    public LecturerAssignmentResponse assignLecturer(AssignLecturerRequest request) {

        var lecturer = getLecturerById(request.lecturerId());

        var classroom = getClassById(request.classroomId());

        var subject = getSubjectById(request.subjectId());

        if (lecturerRepository.existsByLecturer_IdAndClassroom_IdAndSubject_IdAndTime(
                lecturer.getId(), classroom.getId(), subject.getId(), request.time())) {
            throw new BadRequestException("Lecturer already assigned for this classroom, subject and time");
        }

        if (lecturerRepository.existsByClassroom_IdAndSubject_Id(classroom.getId(),subject.getId())){
            throw new BadRequestException("This subject already have lecturer");
        }

        var assignment = LecturerAssignment.builder()
                .lecturer(lecturer)
                .classroom(classroom)
                .subject(subject)
                .time(request.time())
                .assignedDate(LocalDate.now())
                .build();

        var saved = lecturerRepository.save(assignment);

        return lecturerMapper.toResponse(saved);
    }

    @Override
    public PageResponse<LecturerAssignmentResponse> getLecturerAssignments(Long lecturerId, String lecturerName, String lecturerCode, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<LecturerAssignment> spec = ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (lecturerId != null){
                predicates.add(cb.equal(root.join("lecturer").get("id"), lecturerId));
            }

            if (lecturerName != null){
                predicates.add(cb.equal(root.join("lecturer").get("fullName"), lecturerName));
            }

            if (lecturerCode != null){
                predicates.add(cb.equal(root.join("lecturer").get("staffCode"), lecturerCode));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        List<String> allowSort = List.of("lecturer.id","lecturer.fullName","lecturer.staffCode");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1 ,size,sort);
        Page<LecturerAssignment> lecturerPage = lecturerRepository.findAll(spec,pageable);
        return PageResponse.from(lecturerPage,lecturerMapper::toResponse);
    }

    @Override
    public List<LecturerAssignmentResponse> getClassroomAssignments(Long classroomId) {
        return lecturerRepository.findByClassroom_Id(classroomId)
                .stream()
                .map(lecturerMapper::toResponse)
                .toList();
    }

    @Override
    public void removeAssignment(Long id) {
        if (!lecturerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Assignment not found");
        }
        lecturerRepository.deleteById(id);
    }

    @Override
    public LecturerAssignmentResponse update(Long id,AssignLecturerRequest request) {
        var lecturer = getLecturerById(request.lecturerId());

        var classroom = getClassById(request.classroomId());

        var subject = getSubjectById(request.subjectId());

        var assign = lecturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + id));

        if (lecturerRepository.existsByLecturer_IdAndClassroom_IdAndSubject_IdAndTime(
                lecturer.getId(), classroom.getId(), subject.getId(), request.time())) {
            throw new BadRequestException("Lecturer already assigned for this classroom, subject and time");
        }
        assign.setLecturer(lecturer);
        assign.setClassroom(classroom);
        assign.setSubject(subject);
        assign.setTime(request.time());
        var saved = lecturerRepository.save(assign);
        return lecturerMapper.toResponse(saved);
    }

    private Staff getLecturerById(Long id){
        return staffRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + id));
    }

    private Classroom getClassById(Long id){
        return classroomRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id " + id));
    }

    private Subject getSubjectById(Long id){
        return subjectRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + id));
    }

}