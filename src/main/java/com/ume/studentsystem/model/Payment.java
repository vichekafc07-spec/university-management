package com.ume.studentsystem.model;

import com.ume.studentsystem.config.EntityAuditListener;
import com.ume.studentsystem.model.audit.AuditEntity;
import com.ume.studentsystem.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@EntityListeners(EntityAuditListener.class)
@Getter
@Setter
@SQLDelete(sql = "UPDATE payments SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted = false")
public class Payment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id",foreignKey = @ForeignKey(name = "fk_payment_invoice"))
    private Invoice invoice;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private LocalDate paymentDate;
}
