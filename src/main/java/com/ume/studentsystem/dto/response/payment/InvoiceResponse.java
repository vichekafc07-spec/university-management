package com.ume.studentsystem.dto.response.payment;

import com.ume.studentsystem.model.enums.PaymentStatus;

import java.time.LocalDate;

public record InvoiceResponse(
        Long id,
        String invoiceNo,

        String studentName,
        String studentCode,

        Double totalAmount,
        Double paidAmount,
        PaymentStatus status,
        LocalDate issueDate,
        LocalDate dueDate
) {
}
