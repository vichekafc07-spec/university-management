package com.ume.studentsystem.dto.request.payment;

import com.ume.studentsystem.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull(message = "invoice id required")
        Long invoiceId,

        @NotNull(message = "amount required")
        Double amount,

        @NotNull(message = "payment method required")
        PaymentMethod method
) {}
