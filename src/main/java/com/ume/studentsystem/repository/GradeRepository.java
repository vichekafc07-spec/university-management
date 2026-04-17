package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByStudentSubject_Id(Long studentSubjectId);
}
