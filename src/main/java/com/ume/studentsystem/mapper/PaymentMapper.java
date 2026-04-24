package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.payment.InvoiceRequest;
import com.ume.studentsystem.dto.request.payment.PaymentRequest;
import com.ume.studentsystem.dto.response.payment.InvoiceResponse;
import com.ume.studentsystem.dto.response.payment.PaymentResponse;
import com.ume.studentsystem.model.Invoice;
import com.ume.studentsystem.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "studentId", target = "student.id")
    Invoice toEntity(InvoiceRequest request);

    @Mapping(source = "student.fullName" , target = "studentName")
    @Mapping(source = "student.studentCode" , target = "studentCode")
    InvoiceResponse toResponse(Invoice invoice);

    void updateInvoice(InvoiceRequest request, @MappingTarget Invoice invoice);

    @Mapping(source = "invoiceId" , target = "invoice.id")
    Payment toEntity(PaymentRequest request);

    @Mapping(source = "invoice.invoiceNo", target = "invoiceNo")
    @Mapping(source = "invoice.student.fullName", target = "studentName")
    @Mapping(source = "invoice.student.studentCode", target = "studentCode")
    @Mapping(source = "invoice.totalAmount", target = "totalAmount")
    @Mapping(source = "invoice.paidAmount", target = "paidAmount")
    @Mapping(source = "invoice.status", target = "paymentStatus")
    PaymentResponse toResponse(Payment payment);

}
