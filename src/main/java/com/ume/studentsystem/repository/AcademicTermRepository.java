package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.AcademicTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AcademicTermRepository extends JpaRepository<AcademicTerm,Long>, JpaSpecificationExecutor<AcademicTerm> {
    Optional<AcademicTerm> findAcademicTermByYearAndSemester(Integer year, Integer semester);

    @Query(value = "select * from academic_terms where id = :id" , nativeQuery = true)
    Optional<AcademicTerm> findByIdIncludeDeleted(@Param("id") Long id);

}
