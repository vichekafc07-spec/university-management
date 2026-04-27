package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.payment.PaymentRequest;
import com.ume.studentsystem.dto.response.payment.PaymentResponse;
import com.ume.studentsystem.email.EmailService;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.helper.NotificationHelper;
import com.ume.studentsystem.mapper.PaymentMapper;
import com.ume.studentsystem.model.enums.NotificationType;
import com.ume.studentsystem.model.enums.PaymentStatus;
import com.ume.studentsystem.repository.InvoiceRepository;
import com.ume.studentsystem.repository.NotificationRepository;
import com.ume.studentsystem.repository.PaymentRepository;
import com.ume.studentsystem.service.PaymentService;
import com.ume.studentsystem.service.ReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final NotificationRepository notificationRepository;
    private final ReportService reportService;
    private final InvoiceRepository invoiceRepository;
    private final PaymentMapper paymentMapper;
    private final EmailService emailService;

    @Transactional
    public PaymentResponse pay(PaymentRequest request) {

        var invoice = invoiceRepository.findById(request.invoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        var payment = paymentMapper.toEntity(request);
        payment.setInvoice(invoice);
        payment.setPaymentDate(LocalDate.now());

        paymentRepository.save(payment);

        var notification = NotificationHelper.send(
                invoice.getStudent().getId(),
                "Payment Received",
                "We received $" + request.amount(),
                NotificationType.PAYMENT
        );
        notificationRepository.save(notification);

        var receiptStream = reportService.generateReceipt(payment.getId());

        byte[] pdf = receiptStream.readAllBytes();

        emailService.sendWithAttachment(
                invoice.getStudent().getEmail(),
                "Payment Receipt",
                """
                Dear %s,
        
                Thank you for your payment of $%.2f.
        
                Your receipt is attached.
        
                Regards,
                Finance Office
                """
                        .formatted(
                                invoice.getStudent()
                                        .getFullName(),
                                request.amount()
                        ),
                pdf,
                "receipt_"
                        + invoice.getInvoiceNo()
                        + ".pdf"
        );

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

    @Scheduled(cron = "0 0 8 * * *")
    public void remindDuePayments() {

        LocalDate target =
                LocalDate.now().plusDays(3);

        var invoices = invoiceRepository
                        .findByDueDateAndStatusNot(
                                target,
                                PaymentStatus.PAID
                        );

        for (var invoice : invoices) {

            emailService.send(
                    invoice.getStudent().getEmail(),
                    "Tuition Payment Reminder",
                    """
                    Dear %s,
    
                    Your tuition payment is due on %s.
    
                    Amount: $%.2f
    
                    Please pay on time.
                    """
                    .formatted(
                       invoice.getStudent().getFullName(),
                       invoice.getDueDate(),
                       invoice.getTotalAmount()
                    )
            );
        }
    }


    @Override
    public void delete(Long id) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Payment not found with id " + id));
        paymentRepository.delete(payment);
    }
}
