package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.payment.InvoiceRequest;
import com.ume.studentsystem.dto.response.payment.InvoiceResponse;
import com.ume.studentsystem.service.InvoiceService;
import com.ume.studentsystem.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<APIResponse<InvoiceResponse>> create(@RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(APIResponse.ok(invoiceService.createInvoice(request)));
    }
}
