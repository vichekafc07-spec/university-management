package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.payment.InvoiceRequest;
import com.ume.studentsystem.dto.response.payment.InvoiceResponse;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.PaymentMapper;
import com.ume.studentsystem.model.Invoice;
import com.ume.studentsystem.model.enums.PaymentStatus;
import com.ume.studentsystem.repository.InvoiceRepository;
import com.ume.studentsystem.repository.StudentRepository;
import com.ume.studentsystem.service.InvoiceService;
import com.ume.studentsystem.spec.SpecificationBuilder;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        studentEntity.setIssueDate(LocalDate.now());

        var saved = invoiceRepository.save(studentEntity);

        return paymentMapper.toResponse(saved);
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest request) {
        var invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id " + id));
        var student = studentRepository.findById(request.studentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + request.studentId()));
        paymentMapper.updateInvoice(request,invoice);
        invoice.setStudent(student);

        var saved = invoiceRepository.save(invoice);

        return paymentMapper.toResponse(saved);
    }

    @Override
    public PageResponse<InvoiceResponse> getInvoice(Long id,String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Invoice> spec = new SpecificationBuilder<Invoice>()
                .equal("id",id)
                .build();
        List<String> allowSort = List.of("id");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1 ,size,sort);
        Page<Invoice> invoicePage = invoiceRepository.findAll(spec,pageable);
        return PageResponse.from(invoicePage,paymentMapper::toResponse);
    }

    @Override
    public void deleteInvoice(Long id) {
        var invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id " + id));
        invoiceRepository.delete(invoice);
    }
}
