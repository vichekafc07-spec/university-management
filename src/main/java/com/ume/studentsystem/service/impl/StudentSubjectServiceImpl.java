package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.AssignStudentSubjectRequest;
import com.ume.studentsystem.dto.response.StudentSubjectResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.StudentSubjectMapper;
import com.ume.studentsystem.model.StudentSubject;
import com.ume.studentsystem.model.enums.StudentStatus;
import com.ume.studentsystem.repository.*;
import com.ume.studentsystem.service.StudentSubjectService;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentSubjectServiceImpl implements StudentSubjectService {

    private final StudentClassroomRepository scRepository;
    private final SubjectRepository subjectRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final StudentSubjectMapper ssMapper;

    @Transactional
    @Override
    public List<StudentSubjectResponse> assignSubjects(AssignStudentSubjectRequest request) {

        var studentClassrooms = new HashSet<>(scRepository.findAllWithFullStudentInfo(request.studentId(),request.classroomId()));

        if (studentClassrooms.size() != request.studentId().size()){
            throw new BadRequestException("Some student are not assigned to classroom Id " + request.classroomId());
        }

        if (studentClassrooms.isEmpty()) {
            throw new ResourceNotFoundException("No valid Student found");
        }

        var subjects = new HashSet<>(subjectRepository.findAllById(request.subjectIds()));
        if (subjects.isEmpty()) {
            throw new ResourceNotFoundException("No valid subjects found");
        }

        var existing = studentSubjectRepository.findByStudentClassroomStudent_IdInAndSubjectIdIn(request.studentId(),request.subjectIds());

        Set<String> existingPairs = existing.stream()
                .map(es -> es.getStudentClassroom().getId() + "-" + es.getSubject().getId())
                .collect(Collectors.toSet());

        List<StudentSubject> toSave = new ArrayList<>();

        for (var sc : studentClassrooms) {
            for (var subject : subjects) {

                String key = sc.getId() + "-" + subject.getId();
                if (existingPairs.contains(key)) {
                    throw new BadRequestException("Some student already assign to this subject");
                }

                var ss = new StudentSubject();
                ss.setStudentClassroom(sc);
                ss.setSubject(subject);
                ss.setClassroom(sc.getClassroom());
                ss.setEnrollDate(LocalDate.now());
                ss.setStatus(StudentStatus.ACTIVE);

                toSave.add(ss);
            }
        }
        var saved = studentSubjectRepository.saveAll(toSave);

        return saved.stream()
                .map(ssMapper::toResponse)
                .toList();
    }

    @Override
    public PageResponse<StudentSubjectResponse> getSubjectsByStudent(Long studentId, String studentName, String studentCode, Integer semester, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<StudentSubject> spec = ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (studentId != null){
                predicates.add(cb.equal(root.join("studentClassroom").get("student").get("id"), studentId));
            }
            if (studentName != null){
                predicates.add(cb.like(root.join("studentClassroom").get("student").get("fullName"),"%" + studentName + "%"));
            }
            if (studentCode != null){
                predicates.add(cb.like(root.join("studentClassroom").get("student").get("studentCode"), "%" +studentCode + "%"));
            }
            if (semester != null){
                predicates.add(cb.equal(root.join("studentClassroom").get("classroom").get("semester"),"%" + semester + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        });
        List<String> allowSort = List.of("studentClassroom.student.studentId","studentClassroom.student.fullName","studentClassroom.student.studentCode","studentClassroom.classroom.semester");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1 ,size,sort);
        Page<StudentSubject> subjectPage = studentSubjectRepository.findAll(spec,pageable);
        return PageResponse.from(subjectPage,ssMapper::toResponse);
    }

    @Override
    public PageResponse<StudentSubjectResponse> getStudentBySubject(Long subjectId,Integer semester, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<StudentSubject> spec = ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (subjectId != null){
                predicates.add(cb.equal(root.join("subject").get("id"), subjectId));
            }
            if (semester != null){
                predicates.add(cb.equal(root.join("studentClassroom").get("classroom").get("semester"), semester));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        });
        List<String> allowSort = List.of("subject.id","studentClassroom.classroom.semester");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page - 1 ,size,sort);
        Page<StudentSubject> subjectPage = studentSubjectRepository.findAll(spec,pageable);
        return PageResponse.from(subjectPage,ssMapper::toResponse);
    }
}
