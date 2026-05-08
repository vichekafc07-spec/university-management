package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.StudentClassroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, Long> , JpaSpecificationExecutor<StudentClassroom> {

    List<StudentClassroom> findByClassroomId(Long classroomId);

    List<StudentClassroom> findByStudentId(Long studentId);

    boolean existsByStudentIdAndClassroomId(Long studentId, Long classroomId);

    @Query("""
    SELECT sc FROM StudentClassroom sc
    JOIN FETCH sc.classroom c
    WHERE sc.id IN :sc_Ids
    AND sc.classroom.id = :classroomId
    """)
    List<StudentClassroom> findAllWithFullStudentInfo(
            @Param("sc_Ids") Set<Long> sc_Ids,
            @Param("classroomId") Long classroomId
    );

    @Query("""
        SELECT sc FROM StudentClassroom sc
        JOIN FETCH sc.student s
        JOIN FETCH sc.classroom c
        WHERE c.id = :classroomId
        ORDER BY s.studentCode
        """)
    List<StudentClassroom> findAllByClassroomId(@Param("classroomId") Long classroomId);

    @Query("""
    SELECT sc FROM StudentClassroom sc
    JOIN FETCH sc.student s
    JOIN FETCH sc.classroom c
    WHERE c.id = :classroomId
    ORDER BY s.studentCode
    """)
    List<StudentClassroom> findStudents(Long classroomId);
}