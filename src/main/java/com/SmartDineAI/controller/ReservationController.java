package com.SmartDineAI.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.dto.reservation.CreateReservationRequest;
import com.SmartDineAI.dto.reservation.ReservationResponse;
import com.SmartDineAI.dto.reservation.UpdateReservationRequest;
import com.SmartDineAI.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<ReservationResponse> createReservation(@RequestBody @Valid CreateReservationRequest request){
        ApiResponse<ReservationResponse> response = new ApiResponse<>();
        response.setResult(reservationService.createReservation(request));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<ReservationResponse> getReservationById(@PathVariable Long id){
        ApiResponse<ReservationResponse> response = new ApiResponse<>();
        response.setResult(reservationService.getReservationById(id));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<List<ReservationResponse>> getAllReservation(){
        ApiResponse<List<ReservationResponse>> response = new ApiResponse<>();
        response.setResult(reservationService.getAllReservation());
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<ReservationResponse> updateReservation(@PathVariable Long id, @RequestBody @Valid UpdateReservationRequest request){
        ApiResponse<ReservationResponse> response = new ApiResponse<>();
        response.setResult(reservationService.updateReservation(id, request));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return ApiResponse.<String>builder()
                .message("Reservation deleted successfully")
                .build();
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ReservationResponse> searchReservations(
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) String keyword
    ) {
        return reservationService.searchReservation(
                restaurantId, statusId, from, to, keyword
        );
    }
}
