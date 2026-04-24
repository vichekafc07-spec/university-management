package com.ume.studentsystem.dto.request.payment;

import com.ume.studentsystem.model.enums.PaymentMethod;

public record PaymentRequest(
        Long invoiceId,
        Double amount,
        PaymentMethod method
) {}
