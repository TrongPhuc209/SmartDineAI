package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.entity.PaymentStatus;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.repository.PaymentStatusRepository;

@Service
public class PaymentStatusService {
    @Autowired
    PaymentStatusRepository paymentStatusRepository;

    public PaymentStatus getPaymentStatusById(Long id){
        return paymentStatusRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_STATUS_NOT_FOUND));
    }

    public List<PaymentStatus> getAllPaymentStatus(){
        return paymentStatusRepository.findAll();
    }
}
