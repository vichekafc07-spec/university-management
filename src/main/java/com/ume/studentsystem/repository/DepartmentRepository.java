package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    Optional<Department> findByNameIgnoreCase(String name);

    List<Department> findByFaculty_Id(Byte facultyId);
}
