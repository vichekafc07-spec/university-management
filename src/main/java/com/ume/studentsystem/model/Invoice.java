package com.ume.studentsystem.model;

import com.ume.studentsystem.config.EntityAuditListener;
import com.ume.studentsystem.model.audit.AuditEntity;
import com.ume.studentsystem.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@EntityListeners(EntityAuditListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE invoices SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted = false")
public class Invoice extends AuditEntity {

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
