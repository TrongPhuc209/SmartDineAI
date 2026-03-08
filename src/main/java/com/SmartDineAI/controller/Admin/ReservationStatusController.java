package com.SmartDineAI.controller.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.entity.ReservationStatus;
import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.service.ReservationStatusService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/reservation-statuses")
public class ReservationStatusController {
    @Autowired
    private ReservationStatusService reservationStatusService;

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<ReservationStatus> getReservationStatusById(@PathVariable Long id){
        ApiResponse<ReservationStatus> response = new ApiResponse<>();
        response.setResult(reservationStatusService.getReservationStatusById(id));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get-all")
    public ApiResponse<Page<ReservationStatus>> getAllReservationStatus(Pageable pageable){
        Page<ReservationStatus> result = reservationStatusService.getAllReservationStatus(pageable);
        return ApiResponse.<Page<ReservationStatus>>builder()
                .result(result)
                .build();
    }
}
