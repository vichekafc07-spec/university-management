package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.TimetableRequest;
import com.ume.studentsystem.dto.response.TimetableResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.TimetableMapper;
import com.ume.studentsystem.model.AcademicTerm;
import com.ume.studentsystem.model.Timetable;
import com.ume.studentsystem.repository.*;
import com.ume.studentsystem.service.TimetableService;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import jakarta.persistence.criteria.Join;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;
    private final AcademicTermRepository termRepository;
    private final ClassroomRepository classroomRepository;
    private final SubjectRepository subjectRepository;
    private final StaffRepository staffRepository;
    private final RoomRepository roomRepository;
    private final TimetableMapper mapper;

    @Override
    public TimetableResponse create(TimetableRequest request) {

        if (request.startTime().isAfter(request.endTime()) ||
                request.startTime().equals(request.endTime())) {
            throw new BadRequestException("Invalid time range");
        }

        var conflicts = timetableRepository.findConflicts(
                request.dayOfWeek(),
                request.startTime(),
                request.endTime(),
                request.roomId(),
                request.lecturerId(),
                request.classroomId()
        );

        if (!conflicts.isEmpty()) {
            throw new BadRequestException("Schedule conflict detected");
        }

        var timetable = mapper.toEntity(request);

        timetable.setTerm(termRepository.findByIdAndDeletedFalse(request.termId())
                        .orElseThrow(() -> new ResourceNotFoundException("Term not found")));

        timetable.setClassroom(classroomRepository.findByIdAndDeletedFalse(request.classroomId())
                        .orElseThrow(() -> new ResourceNotFoundException("Classroom not found")));

        timetable.setSubject(subjectRepository.findByIdAndDeletedFalse(request.subjectId())
                        .orElseThrow(() -> new ResourceNotFoundException("Subject not found")));

        timetable.setLecturer(staffRepository.findByIdAndDeletedFalse(request.lecturerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found")));

        timetable.setRoom(roomRepository.findByIdAndDeletedFalse(request.roomId())
                        .orElseThrow(() -> new ResourceNotFoundException("Room not found")));

        timetableRepository.save(timetable);

        return mapper.toResponse(timetable);
    }

    @Override
    public List<TimetableResponse> getByClassroom(Long classroomId) {
        return timetableRepository.findByClassroom_Id(classroomId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<TimetableResponse> getByLecturer(Long lecturerId) {
        return timetableRepository.findByLecturer_Id(lecturerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        if (!timetableRepository.existsById(id)) {
            throw new ResourceNotFoundException("Timetable not found");
        }

        timetableRepository.deleteById(id);
    }

    @Override
    public PageResponse<TimetableResponse> getAllTime(Long id, LocalDate startDate, LocalDate endDate, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Timetable> spec = ((root, query, cb) -> {
            Join<Timetable, AcademicTerm> termJoin = root.join("term");
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(cb.equal(termJoin.get("id"), id));
            }

            if (startDate != null && endDate != null) {
                predicates.add(cb.between(termJoin.get("startDate"), startDate, endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });


        List<String> allowSort = List.of("term.id","term.startDate","term.endDate");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page - 1,size,sort);
        Page<Timetable> timetablePage = timetableRepository.findAll(spec,pageable);
        return PageResponse.from(timetablePage,mapper::toResponse);
    }
}
