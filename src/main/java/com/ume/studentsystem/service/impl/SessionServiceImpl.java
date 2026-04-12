package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.SessionRequest;
import com.ume.studentsystem.dto.response.SessionResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.SessionMapper;
import com.ume.studentsystem.model.Session;
import com.ume.studentsystem.repository.LecturerAssignmentRepository;
import com.ume.studentsystem.repository.RoomRepository;
import com.ume.studentsystem.repository.SessionRepository;
import com.ume.studentsystem.service.SessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final LecturerAssignmentRepository lecturerAssignmentRepository;
    private final RoomRepository roomRepository;
    private final SessionMapper mapper;

    public SessionResponse create(SessionRequest request) {

        var la = lecturerAssignmentRepository.findById(request.lecturerAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found"));

        var room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (request.startTime().isAfter(request.endTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        var roomConflicts = sessionRepository.findRoomConflicts(
                room.getId(),
                request.day(),
                request.startTime(),
                request.endTime()
        );

        if (!roomConflicts.isEmpty()) {
            throw new BadRequestException("Room already booked at this time");
        }

        var lecturerConflicts = sessionRepository.findLecturerConflicts(
                la.getLecturer().getId(),
                request.day(),
                request.startTime(),
                request.endTime()
        );

        if (!lecturerConflicts.isEmpty()) {
            throw new BadRequestException("Lecturer already has a session at this time");
        }

        var session = new Session();
        session.setLecturerAssignment(la);
        session.setRoom(room);
        session.setDay(request.day());
        session.setStartTime(request.startTime());
        session.setEndTime(request.endTime());

        sessionRepository.save(session);

        return mapper.toResponse(session);
    }

    public List<SessionResponse> getAll() {
        return sessionRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void delete(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Session not found");
        }
        sessionRepository.deleteById(id);
    }
}
