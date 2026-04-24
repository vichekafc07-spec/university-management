package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.payment.InvoiceRequest;
import com.ume.studentsystem.dto.response.payment.InvoiceResponse;

public interface InvoiceService {
    InvoiceResponse createInvoice(InvoiceRequest request);
}
