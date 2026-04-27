package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.AttendanceRequest;
import com.ume.studentsystem.dto.request.UpdateAttendanceStatus;
import com.ume.studentsystem.dto.response.AttendanceResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.helper.NotificationHelper;
import com.ume.studentsystem.mapper.AttendanceMapper;
import com.ume.studentsystem.model.Attendance;
import com.ume.studentsystem.model.enums.AttendanceStatus;
import com.ume.studentsystem.model.enums.NotificationType;
import com.ume.studentsystem.repository.AttendanceRepository;
import com.ume.studentsystem.repository.NotificationRepository;
import com.ume.studentsystem.repository.SessionRepository;
import com.ume.studentsystem.repository.StudentSubjectRepository;
import com.ume.studentsystem.service.AttendanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final NotificationRepository notificationRepository;
    private final SessionRepository sessionRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public List<AttendanceResponse> markAttendance(AttendanceRequest request) {

        var studentSubjects = studentSubjectRepository.findAllById(request.studentSubjectIds());
        if (studentSubjects.isEmpty()) {
            throw new ResourceNotFoundException("No valid StudentSubject found");
        }

        var session = sessionRepository.findById(request.sessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id " + request.sessionId()));

        List<Attendance> toSave = new ArrayList<>();

        for (var ss : studentSubjects) {

            if (!ss.getSubject().getId().equals(session.getLecturerAssignment().getSubject().getId())){
                throw new BadRequestException("Student is not enrolled in this session's subject");
            }

            if (attendanceRepository.existsByStudentSubject_IdAndSession_IdAndDate(ss.getId(),request.sessionId(), request.date())) {
                continue;
            }

            var attendance = new Attendance();
            attendance.setStudentSubject(ss);
            attendance.setSession(session);
            attendance.setDate(request.date());
            attendance.setStatus(request.status());

            toSave.add(attendance);
        }

        var saved = attendanceRepository.saveAll(toSave);

        for (var att : saved){
            if (att.getStatus() == AttendanceStatus.ABSENT){
                Long studentId = att.getStudentSubject().getStudentClassroom().getStudent().getId();
                long absentCount = attendanceRepository.countStudentAttendanceStatus(studentId,AttendanceStatus.ABSENT);

                if (absentCount >= 3){
                    var note = NotificationHelper.send(
                            studentId,
                            "Attendance Warning",
                            "You already have " + absentCount + " absent",
                            NotificationType.ATTENDANCE
                    );
                    notificationRepository.save(note);
                }
            }
        }

        return saved.stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    @Override
    public List<AttendanceResponse> getByStudent(Long studentId) {
        return attendanceRepository.findByStudentSubject_StudentClassroomId(studentId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    public List<AttendanceResponse> getBySubject(Long subjectId) {
        return attendanceRepository.findByStudentSubject_Subject_Id(subjectId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Attendance not found");
        }
        attendanceRepository.deleteById(id);
    }

    @Override
    public AttendanceResponse updateAttendanceStatus(Long id, UpdateAttendanceStatus request) {
        var attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id " + id));
        attendance.setStatus(request.status());
        var saved = attendanceRepository.save(attendance);
        return attendanceMapper.toResponse(saved);
    }
}
