package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Attendance;
import com.ume.studentsystem.model.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByStudentSubject_IdAndSession_IdAndDate(Long studentSubjectId, Long sessionId, LocalDate date);

    List<Attendance> findByStudentSubject_StudentClassroom_Student_Id(Long studentId);

    List<Attendance> findByStudentSubject_Subject_Id(Long subjectId);

    long countByStudentSubject_IdAndStatus(Long studentSubjectId , AttendanceStatus status);
}
