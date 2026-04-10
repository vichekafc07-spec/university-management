package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long>, JpaSpecificationExecutor<Classroom> {

    @Query(value = "select * from classrooms where id = :id" , nativeQuery = true)
    Optional<Classroom> findByIdIncludeDeleted(@Param("id") Long id);

    Optional<Classroom> findByIdAndDeletedFalse(Long id);
}
