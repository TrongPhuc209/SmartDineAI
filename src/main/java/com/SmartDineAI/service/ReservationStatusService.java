package com.SmartDineAI.service;

import java.util.List;

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

    public List<ReservationStatus> getAllReservationStatus(){
        return reservationStatusRepository.findAll();
    }
}
