package com.ume.studentsystem.dto.response.payment;

import com.ume.studentsystem.model.enums.PaymentMethod;

import java.time.LocalDate;

public record PaymentResponse(
        Long id,

        String invoiceNo,
        String studentName,
        String studentCode,

        Double paidAmount,
        Double totalAmount,
        String paymentStatus,

        Double amount,
        PaymentMethod method,
        LocalDate paymentDate
) {
}
