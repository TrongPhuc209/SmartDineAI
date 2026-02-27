package com.SmartDineAI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.repository.ReservationStatusRepository;

@Service
public class ReservationStatusService {
    @Autowired
    ReservationStatusRepository reservationStatusRepository;


    
}
