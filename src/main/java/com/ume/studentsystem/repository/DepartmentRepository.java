package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    Optional<Department> findDepartmentByName(String name);

    List<Department> findByFacultyId(Byte facultyId);
}
