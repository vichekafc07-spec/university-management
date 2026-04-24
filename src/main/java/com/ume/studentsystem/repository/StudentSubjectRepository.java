package com.ume.studentsystem.repository;

import com.ume.studentsystem.dto.response.TranscriptRow;
import com.ume.studentsystem.model.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long>, JpaSpecificationExecutor<StudentSubject> {

    List<StudentSubject> findByStudentClassroomIdInAndSubjectIdIn(Set<Long> studentClassroomIds, Set<Long> subjectIds);

    List<StudentSubject> findByStudentClassroom_Student_Id(Long studentClassroomId);

    List<StudentSubject> findBySubject_Id(Long subjectId);

    @Query("""
    SELECT new com.ume.studentsystem.dto.response.TranscriptRow(ss, g)
    FROM StudentSubject ss
    JOIN FETCH ss.subject s
    JOIN FETCH ss.studentClassroom sc
    JOIN FETCH sc.student st
    LEFT JOIN Grade g ON g.studentSubject = ss
    WHERE st.id = :studentId
    """)
    List<TranscriptRow> findTranscriptData(@Param("studentId") Long studentId);

}