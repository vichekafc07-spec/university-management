package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNo;

    @ManyToOne
    @JoinColumn(name = "student_id",foreignKey = @ForeignKey(name = "fk_invoice_student"))
    private Student student;

    private Double totalAmount;

    private Double paidAmount = 0.0;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDate issueDate;

    private LocalDate dueDate;
}
