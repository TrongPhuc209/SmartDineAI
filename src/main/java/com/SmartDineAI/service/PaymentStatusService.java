package com.SmartDineAI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.repository.PaymentStatusRepository;

@Service
public class PaymentStatusService {
    @Autowired
    PaymentStatusRepository paymentStatusRepository;
    
}
