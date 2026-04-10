package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> , JpaSpecificationExecutor<Permission> {
    Optional<Permission> findByName(String name);
}
