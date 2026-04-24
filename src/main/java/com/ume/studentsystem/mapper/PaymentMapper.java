package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.payment.InvoiceRequest;
import com.ume.studentsystem.dto.response.payment.InvoiceResponse;
import com.ume.studentsystem.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "studentId", target = "student.id")
    Invoice toEntity(InvoiceRequest request);

    @Mapping(source = "student.fullName" , target = "studentName")
    @Mapping(source = "student.studentCode" , target = "studentCode")
    InvoiceResponse toResponse(Invoice invoice);
}
