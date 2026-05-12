package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubjectRepository extends JpaRepository<Subject,Long> , JpaSpecificationExecutor<Subject> {
    boolean existsByCode(String code);

    Optional<Subject> findByIdAndDeletedFalse(Long id);

    @Query("""
    SELECT s
    FROM Subject s
    WHERE s.id IN :ids
    AND s.deleted = false
    """)
    List<Subject> findAllActiveByIds(@Param("ids") Set<Long> ids);

    @Query(value = "SELECT * FROM subjects WHERE id = :id", nativeQuery = true)
    Optional<Subject> findByIdIncludingDeleted(@Param("id") Long id);

}
