package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {

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
