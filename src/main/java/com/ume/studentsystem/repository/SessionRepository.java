package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> , JpaSpecificationExecutor<Session> {


    @Query("""
        SELECT s FROM Session s
        WHERE s.room.id = :roomId
        AND s.day = :day
        AND (
            (:start < s.endTime AND :end > s.startTime)
        )
    """)
    List<Session> findRoomConflicts(
            Integer roomId,
            DayOfWeek day,
            LocalTime start,
            LocalTime end
    );

    @Query("""
        SELECT s FROM Session s
        WHERE s.lecturerAssignment.lecturer.id = :lecturerId
        AND s.day = :day
        AND (
            (:start < s.endTime AND :end > s.startTime)
        )
    """)
    List<Session> findLecturerConflicts(
            Long lecturerId,
            DayOfWeek day,
            LocalTime start,
            LocalTime end
    );

    @Query("""
    SELECT s FROM Session s
    JOIN FETCH s.lecturerAssignment la
    JOIN FETCH la.classroom c
    JOIN FETCH la.subject sub
    JOIN FETCH la.lecturer lec
    LEFT JOIN FETCH s.room r
    WHERE s.id = :id
    """)
    Optional<Session> findFullById(Long id);
}
