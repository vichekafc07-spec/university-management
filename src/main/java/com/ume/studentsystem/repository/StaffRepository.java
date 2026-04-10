package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Staff;
import com.ume.studentsystem.model.enums.StaffPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> , JpaSpecificationExecutor<Staff> {

    @Query(value = "SELECT * FROM staffs WHERE id = :id", nativeQuery = true)
    Optional<Staff> findByIdIncludingDeleted(@Param("id") Long id);

    Optional<Staff> findByIdAndDeletedFalse(Long id);

    List<Staff> findByDepartmentId(Integer departmentId);

    List<Staff> findByFacultyId(Byte facultyId);

    List<Staff> findByPosition(StaffPosition position);

    boolean existsByUserId(Long userId);

    @Query("SELECT s.staffCode FROM Staff s ORDER BY s.id DESC LIMIT 1")
    String findLastStaffCode();

}
