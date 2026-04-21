package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByClassroom_Id(Long classroomId);

    List<Exam> findByInvigilator_Id(Long invigilatorId);

    @Query("""
        SELECT e FROM Exam e
        WHERE e.examDate = :date
        AND (:start < e.endTime AND :end > e.startTime)
        AND (
            e.room.id = :roomId OR
            e.invigilator.id = :staffId OR
            e.classroom.id = :classroomId
        )
    """)
    List<Exam> findConflicts(
            @Param("date") LocalDate date,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end,
            @Param("roomId") Long roomId,
            @Param("staffId") Long staffId,
            @Param("classroomId") Long classroomId
    );
}
