package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.exam.ExamRequest;
import com.ume.studentsystem.dto.response.exam.ExamResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.ExamMapper;
import com.ume.studentsystem.repository.*;
import com.ume.studentsystem.service.ExamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final AcademicTermRepository termRepository;
    private final ClassroomRepository classroomRepository;
    private final SubjectRepository subjectRepository;
    private final RoomRepository roomRepository;
    private final StaffRepository staffRepository;
    private final ExamMapper examMapper;

    @Override
    public ExamResponse create(ExamRequest request) {

        if (!request.startTime().isBefore(request.endTime())) {
            throw new BadRequestException("Invalid time range");
        }

        var conflicts = examRepository.findConflicts(
                request.examDate(),
                request.startTime(),
                request.endTime(),
                request.roomId(),
                request.invigilatorId(),
                request.classroomId()
        );

        if (!conflicts.isEmpty()) {
            throw new BadRequestException("Exam conflict detected");
        }

        var term = termRepository.findById(request.termId())
                .orElseThrow(() -> new ResourceNotFoundException("Term not found with id: " + request.termId()));
        var classroom = classroomRepository.findById(request.classroomId())
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id: " + request.classroomId()));
        var subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + request.subjectId()));
        var room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.roomId()));
        var invigilator = staffRepository.findById(request.invigilatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + request.invigilatorId()));


        var exam = examMapper.toEntity(request);

        exam.setTerm(term);
        exam.setClassroom(classroom);
        exam.setSubject(subject);
        exam.setRoom(room);
        exam.setInvigilator(invigilator);

        var saved = examRepository.save(exam);

        return examMapper.toResponse(saved);
    }

    @Override
    public List<ExamResponse> getByClassroom(Long classroomId) {
        return examRepository.findByClassroom_Id(classroomId)
                .stream()
                .map(examMapper::toResponse)
                .toList();
    }

    @Override
    public List<ExamResponse> getByInvigilator(Long staffId) {
        return examRepository.findByInvigilator_Id(staffId)
                .stream()
                .map(examMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!examRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exam not found");
        }
        examRepository.deleteById(id);
    }
}
