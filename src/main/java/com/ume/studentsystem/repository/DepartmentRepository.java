package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Integer> , JpaSpecificationExecutor<Department> {
    Optional<Department> findByNameIgnoreCase(String name);

    List<Department> findByFaculty_Id(Byte facultyId);
}
