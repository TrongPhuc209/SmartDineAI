package com.SmartDineAI.controller.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.entity.PaymentStatus;
import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.service.PaymentStatusService;

@RestController
@RequestMapping("/admin/payment-statuses")
public class PaymentStatusController {
    @Autowired
    private PaymentStatusService paymentStatusService;

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<PaymentStatus> getPaymentStatusById(@PathVariable Long id){
        ApiResponse<PaymentStatus> response = new ApiResponse<>();
        response.setResult(paymentStatusService.getPaymentStatusById(id));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<Page<PaymentStatus>> getAllPaymentStatus(Pageable pageable){
        Page<PaymentStatus> result = paymentStatusService.getAllPaymentStatus(pageable);
        return ApiResponse.<Page<PaymentStatus>>builder()
                .result(result)
                .build();
    }
}
