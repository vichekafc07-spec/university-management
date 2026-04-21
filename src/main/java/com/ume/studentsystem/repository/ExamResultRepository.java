package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    Optional<ExamResult> findByExam_IdAndStudentSubject_Id(Long examId, Long studentSubjectId);

    List<ExamResult> findByExam_Id(Long examId);

    List<ExamResult> findByStudentSubject_Id(Long studentSubjectId);
}
