package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
