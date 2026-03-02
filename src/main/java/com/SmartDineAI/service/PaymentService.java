package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartDineAI.dto.payment.CreatePaymentRequest;
import com.SmartDineAI.dto.payment.PaymentResponse;
import com.SmartDineAI.dto.payment.UpdatePaymentRequest;
import com.SmartDineAI.entity.Payment;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.mapper.PaymentMapper;
import com.SmartDineAI.repository.PaymentRepository;
import com.SmartDineAI.repository.PaymentStatusRepository;
import com.SmartDineAI.repository.ReservationRepository;

@Service
@Transactional
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    public PaymentResponse createPayment(CreatePaymentRequest request){
        Payment payment =  paymentMapper.toPayment(request);
        payment.setReservation(reservationRepository.findById(request.getReservationId())
                                                    .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND)));
        payment.setPaymentStatus(paymentStatusRepository.findById(request.getPaymentStatusId())
                                                    .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_STATUS_NOT_FOUND)));
        paymentRepository.save(payment);
        return paymentMapper.toResponse(payment);
    }

    public PaymentResponse getPaymentById(Long PaymentId){
        return paymentMapper.toResponse(paymentRepository.findById(PaymentId)
                                                        .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND)));
    }

    public List<PaymentResponse> getAllPayment(){
        return paymentRepository.findAll()
                                .stream()
                                .map(paymentMapper::toResponse)
                                .toList();
    }

    public PaymentResponse updatePayment(Long paymentId, UpdatePaymentRequest request){
        Payment payment = paymentRepository.findById(paymentId)
                                            .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        paymentMapper.updatePayment(payment, request);
        payment.setPaymentStatus(paymentStatusRepository.findById(request.getPaymentStatusId())
                                                        .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_STATUS_NOT_FOUND)));
        return paymentMapper.toResponse(payment);
    }
}
