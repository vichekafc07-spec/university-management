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
    JOIN FETCH sc.student s
    LEFT JOIN FETCH s.department d
    LEFT JOIN FETCH d.faculty f
    WHERE sc.student.id IN :studentIds
    AND sc.classroom.id = :classroomId
    """)
    List<StudentClassroom> findAllWithFullStudentInfo(
            @Param("studentIds") Set<Long> studentIds,
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

}