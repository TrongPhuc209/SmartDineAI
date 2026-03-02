package com.SmartDineAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.entity.PaymentStatus;
import com.SmartDineAI.service.PaymentStatusService;

@RestController
@RequestMapping("/admin/payment-statuses")
public class PaymentStatusController {
    @Autowired
    private PaymentStatusService paymentStatusService;

    @GetMapping("/{id}")
    public PaymentStatus getPaymentStatusById(@PathVariable Long id){
        return paymentStatusService.getPaymentStatusById(id);
    }

    @GetMapping("/get-all")
    public List<PaymentStatus> getAllPaymentStatus(){
        return paymentStatusService.getAllPaymentStatus();
    }
}
