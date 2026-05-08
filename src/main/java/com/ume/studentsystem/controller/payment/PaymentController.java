package com.ume.studentsystem.controller.payment;

import com.ume.studentsystem.dto.request.payment.PaymentRequest;
import com.ume.studentsystem.dto.response.payment.PaymentResponse;
import com.ume.studentsystem.service.PaymentService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAuthority('cashier:write')")
    public ResponseEntity<APIResponse<PaymentResponse>> pay(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(APIResponse.ok(paymentService.pay(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('cashier:write')")
    public ResponseEntity<APIResponse<PaymentResponse>> update(@PathVariable Long id,
                                                               @Valid @RequestBody PaymentRequest request){
        return ResponseEntity.ok(APIResponse.ok(paymentService.update(id,request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('cashier:write')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}