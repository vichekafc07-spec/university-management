package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Attendance;
import com.ume.studentsystem.model.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByStudentSubject_IdAndSession_IdAndDate(Long studentSubjectId, Long sessionId, LocalDate date);

    List<Attendance> findByStudentSubject_StudentClassroomId(Long studentSubjectStudentClassroomId);

    List<Attendance> findByStudentSubject_Subject_Id(Long subjectId);

    long countByStudentSubject_IdAndStatus(Long studentSubjectId , AttendanceStatus status);

    @Query("""
    SELECT
    (COUNT(CASE WHEN a.status = 'PRESENT' THEN 1 END) * 100.0)
     / COUNT(a)
    FROM Attendance a
    """)
    Double getAttendancePercent();

    @Query("""
    SELECT count(a)
    from Attendance a where a.studentSubject.studentClassroom.student.id = :studentId
    and a.status = :status
    """)
    long countStudentAttendanceStatus(
            @Param("studentId") Long studentId,
            @Param("status") AttendanceStatus status);
}
