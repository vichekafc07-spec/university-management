package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.TimetableRequest;
import com.ume.studentsystem.dto.response.TimetableResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.TimetableMapper;
import com.ume.studentsystem.repository.*;
import com.ume.studentsystem.service.TimetableService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        timetable.setTerm(termRepository.findById(request.termId())
                        .orElseThrow(() -> new ResourceNotFoundException("Term not found")));

        timetable.setClassroom(classroomRepository.findById(request.classroomId())
                        .orElseThrow(() -> new ResourceNotFoundException("Classroom not found")));

        timetable.setSubject(subjectRepository.findById(request.subjectId())
                        .orElseThrow(() -> new ResourceNotFoundException("Subject not found")));

        timetable.setLecturer(staffRepository.findById(request.lecturerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found")));

        timetable.setRoom(roomRepository.findById(request.roomId())
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
}
