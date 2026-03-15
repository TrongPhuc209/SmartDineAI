package com.SmartDineAI.controller.admin;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.SmartDineAI.dto.diningTable.DiningTableResponse;
import com.SmartDineAI.dto.reservation.CreateReservationRequest;
import com.SmartDineAI.dto.reservation.ReservationResponse;
import com.SmartDineAI.dto.reservation.UpdateReservationRequest;
import com.SmartDineAI.dto.reservation.UpdateReservationStatusRequest;
import com.SmartDineAI.service.ReservationService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<ReservationResponse> createReservation(@RequestBody @Valid CreateReservationRequest request){
        ApiResponse<ReservationResponse> response = new ApiResponse<>();
        response.setResult(reservationService.createReservation(request));
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<ReservationResponse> getReservationById(@PathVariable Long id){
        ApiResponse<ReservationResponse> response = new ApiResponse<>();
        response.setResult(reservationService.getReservationById(id));
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<Page<ReservationResponse>> getAllReservation(Pageable pageable){
        Page<ReservationResponse> response = reservationService.getAllReservation(pageable);
        return ApiResponse.<Page<ReservationResponse>>builder().result(response).build();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/my")
    public ApiResponse<Page<ReservationResponse>> myReservation(Pageable pageable){
        return ApiResponse.<Page<ReservationResponse>>builder()
                                                    .result(reservationService
                                                            .myReservation(pageable))
                                                    .build();
    } 

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<ReservationResponse> updateReservation(@PathVariable Long id, @RequestBody @Valid UpdateReservationRequest request){
        ApiResponse<ReservationResponse> response = new ApiResponse<>();
        response.setResult(reservationService.updateReservation(id, request));
        return response;
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(
            @RequestBody UpdateReservationStatusRequest request
    ){
        reservationService.updateReservationStatus(request);
        return ResponseEntity.ok("Status updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return ApiResponse.<String>builder()
                .message("Reservation deleted successfully")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/my/{id}")
    public ApiResponse<String> myDelete(@PathVariable Long id){
        reservationService.myDelete(id);
        return ApiResponse.<String>builder()
                .message("Reservation deleted successfully")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<Page<ReservationResponse>> searchReservations(
        @RequestParam(required = false) Long restaurantId,
        @RequestParam(required = false) Long statusId,
        @RequestParam(required = false) LocalDateTime from,
        @RequestParam(required = false) LocalDateTime to,
        @RequestParam(required = false) String keyword,
        Pageable pageable
    ) {
        return ApiResponse.<Page<ReservationResponse>>builder()
                                                    .result(reservationService.searchReservation(restaurantId, 
                                                                                                statusId, 
                                                                                                from, 
                                                                                                to, 
                                                                                                keyword,
                                                                                                pageable))
                                                    .build(); 
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/available")
    public Page<DiningTableResponse> findAvailableTablesByTime(
            @RequestParam LocalDateTime start, 
            @RequestParam LocalDateTime end, 
            Pageable pageable
    ) {
        return reservationService.findAvailableTablesByTime(start, end, pageable);
    }

    @GetMapping("/my/{id}")
    public ApiResponse<ReservationResponse> check(@PathVariable Long id){
        return ApiResponse.<ReservationResponse>builder()
                                    .result(reservationService.myReservation(id))
                                    .build();
    }
}
