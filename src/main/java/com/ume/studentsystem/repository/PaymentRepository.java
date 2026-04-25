package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Query("""
    SELECT COALESCE(SUM(p.amount),0)
    FROM Payment p
    WHERE MONTH(p.paymentDate) = :month
    AND YEAR(p.paymentDate) = :year
    """)
    Double getRevenueThisMonth(
            @Param("month") int month,
            @Param("year") int year
    );
}
