package com.ume.studentsystem.repository;

import com.ume.studentsystem.dto.response.RankingResponse;
import com.ume.studentsystem.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByStudentSubject_Id(Long studentSubjectId);
    List<Grade> findByStudentSubject_StudentClassroom_Student_Id(Long studentId);

    @Query("""
    SELECT
    st.id,
    st.fullName,
    st.studentCode,
    SUM(g.gpa * sub.credit),
    SUM(sub.credit)
    FROM Grade g
    JOIN g.studentSubject ss
    JOIN ss.subject sub
    JOIN ss.studentClassroom sc
    JOIN sc.student st
    WHERE sc.classroom.id = :classroomId
    GROUP BY st.id, st.fullName, st.studentCode
    ORDER BY SUM(g.gpa * sub.credit) DESC
    """)
    List<Object[]> rankByClassroomRaw(@Param("classroomId") Long classroomId);

    @Query("""
    SELECT
    st.id,
    st.fullName,
    st.studentCode,
    SUM(g.gpa * sub.credit),
    SUM(sub.credit)
    FROM Grade g
    JOIN g.studentSubject ss
    JOIN ss.subject sub
    JOIN ss.studentClassroom sc
    JOIN sc.student st
    JOIN st.faculty f
    WHERE f.id = :facultyId
    GROUP BY st.id, st.fullName, st.studentCode
    ORDER BY SUM(g.gpa * sub.credit) / SUM(sub.credit) DESC
    """)
    List<Object[]> findTopStudentsByFacultyRaw(
            @Param("facultyId") Long facultyId
    );

}
