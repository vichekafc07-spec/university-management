package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.payment.InvoiceRequest;
import com.ume.studentsystem.dto.response.payment.InvoiceResponse;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.PaymentMapper;
import com.ume.studentsystem.model.enums.PaymentStatus;
import com.ume.studentsystem.repository.InvoiceRepository;
import com.ume.studentsystem.repository.StudentRepository;
import com.ume.studentsystem.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentMapper paymentMapper;
    private final StudentRepository studentRepository;

    @Override
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        var student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        var studentEntity = paymentMapper.toEntity(request);
        studentEntity.setStudent(student);
        studentEntity.setStatus(PaymentStatus.PENDING);
        studentEntity.setInvoiceNo("IVN-"+ System.currentTimeMillis());

        var saved = invoiceRepository.save(studentEntity);

        return paymentMapper.toResponse(saved);
    }
}
