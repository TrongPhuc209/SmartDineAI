package com.SmartDineAI.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    public Page<PaymentStatus> getAllPaymentStatus(Pageable pageable){
        return paymentStatusRepository.findAll(pageable);
    }
}
