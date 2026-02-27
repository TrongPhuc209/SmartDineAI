package com.SmartDineAI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.repository.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

}
