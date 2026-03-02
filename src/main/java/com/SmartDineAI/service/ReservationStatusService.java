package com.SmartDineAI.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.entity.ReservationStatus;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.repository.ReservationStatusRepository;

@Service
public class ReservationStatusService {
    @Autowired
    ReservationStatusRepository reservationStatusRepository;

    public ReservationStatus getReservationStatusById(Long id){
        return reservationStatusRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_STATUS_NOT_FOUND));
    }

    public Page<ReservationStatus> getAllReservationStatus(Pageable pageable){
        return reservationStatusRepository.findAll(pageable);
    }
}
