package com.SmartDineAI.dto.reservation;

import lombok.Data;

@Data
public class UpdateReservationStatusRequest {
    private Long reservationId;
    private String status;
}
