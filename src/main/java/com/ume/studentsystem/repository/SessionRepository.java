package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {


    @Query("""
        SELECT s FROM Session s
        WHERE s.room.id = :roomId
        AND s.day = :day
        AND (
            (:start < s.endTime AND :end > s.startTime)
        )
    """)
    List<Session> findRoomConflicts(
            Long roomId,
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
}
