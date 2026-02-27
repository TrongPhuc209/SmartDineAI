package com.SmartDineAI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.repository.ReservationRepository;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

}
