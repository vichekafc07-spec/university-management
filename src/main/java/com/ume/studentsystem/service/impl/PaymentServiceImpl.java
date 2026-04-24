package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.payment.PaymentRequest;
import com.ume.studentsystem.dto.response.payment.PaymentResponse;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.PaymentMapper;
import com.ume.studentsystem.model.enums.PaymentStatus;
import com.ume.studentsystem.repository.InvoiceRepository;
import com.ume.studentsystem.repository.PaymentRepository;
import com.ume.studentsystem.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponse pay(PaymentRequest request) {

        var invoice = invoiceRepository.findById(request.invoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        var payment = paymentMapper.toEntity(request);
        payment.setInvoice(invoice);
        payment.setPaymentDate(LocalDate.now());

        paymentRepository.save(payment);

        // UPDATE INVOICE
        double newPaid = invoice.getPaidAmount() + request.amount();
        invoice.setPaidAmount(newPaid);

        if (newPaid >= invoice.getTotalAmount()) {
            invoice.setStatus(PaymentStatus.PAID);
        } else if (newPaid > 0) {
            invoice.setStatus(PaymentStatus.PARTIAL);
        }

        invoiceRepository.save(invoice);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse update(Long id, PaymentRequest request) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Payment not found with id " + id));
        var invoice = invoiceRepository.findById(request.invoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id " + request.invoiceId()));
        payment.setInvoice(invoice);
        payment.setAmount(request.amount());
        payment.setMethod(request.method());

        double newPaid = invoice.getPaidAmount() + request.amount();
        invoice.setPaidAmount(newPaid);

        if (newPaid >= invoice.getTotalAmount()) {
            invoice.setStatus(PaymentStatus.PAID);
        } else if (newPaid > 0) {
            invoice.setStatus(PaymentStatus.PARTIAL);
        }

        invoiceRepository.save(invoice);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public void delete(Long id) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Payment not found with id " + id));
        paymentRepository.delete(payment);
    }
}
