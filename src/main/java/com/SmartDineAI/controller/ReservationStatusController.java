package com.SmartDineAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.entity.ReservationStatus;
import com.SmartDineAI.service.ReservationStatusService;

@RestController
@RequestMapping("/admin/reservation-statuses")
public class ReservationStatusController {
    @Autowired
    private ReservationStatusService reservationStatusService;

    @GetMapping("/{id}")
    public ReservationStatus getReservationStatusById(@PathVariable Long id){
        return reservationStatusService.getReservationStatusById(id);
    }

    @GetMapping("get-all")
    public List<ReservationStatus> getAllReservationStatus(){
        return reservationStatusService.getAllReservationStatus();
    }
}
