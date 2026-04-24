package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> , JpaSpecificationExecutor<Invoice> {
}
