package com.ume.studentsystem.dto.request.payment;

import java.time.LocalDate;

public record InvoiceRequest(
        Long studentId,
        Double totalAmount,
        LocalDate dueDate
) {}