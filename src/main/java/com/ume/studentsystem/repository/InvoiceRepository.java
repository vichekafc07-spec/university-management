package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> , JpaSpecificationExecutor<Invoice> {
    @Query("""
    SELECT COUNT(DISTINCT i.student.id)
    FROM Invoice i
    WHERE i.status = 'PAID'
    """)
    Long countPaidStudents();

    @Query("""
    SELECT COUNT(DISTINCT i.student.id)
    FROM Invoice i
    WHERE i.status <> 'PAID'
    """)
    Long countUnpaidStudents();



}
