package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student,Long> , JpaSpecificationExecutor<Student> {
    boolean existsByFullName(String fullName);
}
