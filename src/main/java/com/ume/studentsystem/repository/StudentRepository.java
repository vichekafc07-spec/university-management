package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> , JpaSpecificationExecutor<Student> {
    boolean existsByStudentCode(String studentCode);
    List<Student> findByGeneration(Integer generation);

    @Query("SELECT COUNT(s) FROM Student s")
    Long countAllStudents();

    @Query(value = "SELECT * FROM students WHERE id =: id", nativeQuery = true)
    Optional<Student> findByIdIncludingDeleted(@Param("id") Long id);

    Optional<Student> findByIdAndDeletedFalse(Long id);
}
