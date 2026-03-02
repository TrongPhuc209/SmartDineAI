package com.SmartDineAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.dto.payment.CreatePaymentRequest;
import com.SmartDineAI.dto.payment.PaymentResponse;
import com.SmartDineAI.dto.payment.UpdatePaymentRequest;
import com.SmartDineAI.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<PaymentResponse> createPayment(@RequestBody @Valid CreatePaymentRequest request){
        ApiResponse<PaymentResponse> response = new ApiResponse<>();
        response.setResult(paymentService.createPayment(request));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<PaymentResponse> getPaymentById(@PathVariable Long id){
        ApiResponse<PaymentResponse> response = new ApiResponse<>();
        response.setResult(paymentService.getPaymentById(id));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<List<PaymentResponse>> getAllPayment(){
        ApiResponse<List<PaymentResponse>> response = new ApiResponse<>();
        response.setResult(paymentService.getAllPayment());
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<PaymentResponse> updatePayment(@PathVariable Long id, @RequestBody @Valid UpdatePaymentRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.updatePayment(id, request))
                .build();
    }
}
