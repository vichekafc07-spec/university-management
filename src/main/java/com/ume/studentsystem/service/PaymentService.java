package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.payment.PaymentRequest;
import com.ume.studentsystem.dto.response.payment.PaymentResponse;
import jakarta.validation.Valid;

public interface PaymentService {
    PaymentResponse pay(PaymentRequest request);

    PaymentResponse update(Long id, @Valid PaymentRequest request);

    void delete(Long id);
}
