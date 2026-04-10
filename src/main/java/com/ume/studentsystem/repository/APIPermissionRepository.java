package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.APIPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface APIPermissionRepository extends JpaRepository<APIPermission, Integer> {
    @Query("SELECT p FROM APIPermission p WHERE p.path = :path AND p.httpMethod = :method")
    List<APIPermission> findAllByPathAndMethod(String path, String method);
}