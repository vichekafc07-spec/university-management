package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.payment.InvoiceRequest;
import com.ume.studentsystem.dto.response.payment.InvoiceResponse;
import com.ume.studentsystem.service.InvoiceService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<APIResponse<InvoiceResponse>> create(@RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(APIResponse.ok(invoiceService.createInvoice(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<InvoiceResponse>> updateInvoice(@PathVariable Long id,
                                                                      @RequestBody InvoiceRequest request){
        return ResponseEntity.ok(APIResponse.ok(invoiceService.updateInvoice(id,request)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<InvoiceResponse>>> getAllInvoice(@RequestParam(required = false) Long id,
                                                                                    @RequestParam(required = false) String sortBy,
                                                                                    @RequestParam(required = false) String sortAs,
                                                                                    @RequestParam(required = false,defaultValue = "1") Integer page,
                                                                                    @RequestParam(required = false,defaultValue = "5") Integer size
                                                                            ){
        return ResponseEntity.ok(APIResponse.ok(invoiceService.getInvoice(id,sortBy,sortAs,page,size)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

}
