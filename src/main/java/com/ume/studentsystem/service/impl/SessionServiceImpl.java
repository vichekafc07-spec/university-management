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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final LecturerAssignmentRepository lecturerAssignmentRepository;
    private final RoomRepository roomRepository;
    private final SessionMapper sessionMapper;

    @Override
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

        return sessionMapper.toResponse(session);
    }

    @Override
    public SessionResponse update(Long id, SessionRequest request) {
        var session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id " + id));
        var la = lecturerAssignmentRepository.findById(request.lecturerAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + request.lecturerAssignmentId()));
        var room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + request.roomId()));
        session.setLecturerAssignment(la);
        session.setRoom(room);
        session.setDay(request.day());
        session.setStartTime(request.startTime());
        session.setEndTime(request.endTime());

        var saved = sessionRepository.save(session);

        return sessionMapper.toResponse(saved);
    }

    @Override
    public PageResponse<SessionResponse> getAll(String subjectName, String roomName, String day, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Session> spec = ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (subjectName != null) {
                predicates.add(cb.like(cb.lower(root.join("lecturerAssignment").get("subject").get("title")), "%" + subjectName.toLowerCase() + "%"));
            }
            if (roomName != null) {
                predicates.add(cb.like(cb.lower(root.join("room").get("name")), "%" + roomName.toLowerCase() + "%"));
            }
            if (day != null) {
                predicates.add(cb.like(cb.lower(root.get("day")), "%" + day.toLowerCase() + "%" ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        });
        List<String> allowSort = List.of("lecturerAssignment.subject.title","room.name","day");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1 ,size,sort);
        Page<Session> sessionPage = sessionRepository.findAll(spec,pageable);
        return PageResponse.from(sessionPage,sessionMapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Session not found");
        }
        sessionRepository.deleteById(id);
    }
}
