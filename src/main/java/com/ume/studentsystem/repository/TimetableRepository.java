package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    List<Timetable> findByClassroom_Id(Long classroomId);

    List<Timetable> findByLecturer_Id(Long lecturerId);

    @Query("""
        SELECT t FROM Timetable t
        WHERE t.dayOfWeek = :day
        AND (:start < t.endTime AND :end > t.startTime)
        AND (
            t.room.id = :roomId OR
            t.lecturer.id = :lecturerId OR
            t.classroom.id = :classroomId
        )
    """)
    List<Timetable> findConflicts(
            @Param("day") DayOfWeek day,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end,
            @Param("roomId") Integer roomId,
            @Param("lecturerId") Long lecturerId,
            @Param("classroomId") Long classroomId
    );
}
