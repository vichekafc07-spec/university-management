package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.payment.InvoiceRequest;
import com.ume.studentsystem.dto.response.payment.InvoiceResponse;
import com.ume.studentsystem.util.PageResponse;

public interface InvoiceService {
    InvoiceResponse createInvoice(InvoiceRequest request);

    InvoiceResponse updateInvoice(Long id, InvoiceRequest request);

    PageResponse<InvoiceResponse> getInvoice(Long id,String sortBy, String sortAs, Integer page, Integer size);

    void deleteInvoice(Long id);
}
