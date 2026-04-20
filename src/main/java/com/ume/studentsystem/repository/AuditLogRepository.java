package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.audit.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
