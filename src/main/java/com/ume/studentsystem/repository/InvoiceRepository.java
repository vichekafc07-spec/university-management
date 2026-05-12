package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Invoice;
import com.ume.studentsystem.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    List<Invoice> findByDueDateAndStatusNot(LocalDate dueDate, PaymentStatus status);

    Optional<Invoice> findByIdAndDeletedFalse(Long id);

    @Query(value = "SELECT * FROM invoices WHERE id = :id", nativeQuery = true)
    Optional<Invoice> findByIdIncludingDeleted(@Param("id") Long id);

}
