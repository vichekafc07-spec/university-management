package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubjectRepository extends JpaRepository<Subject,Long> , JpaSpecificationExecutor<Subject> {
    boolean existsByCode(String code);
}
