package com.SmartDineAI.dto.reservation;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReservationRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int guestCount;

    private Long customerId; // không nên
    private Long diningTableId;
    private Long restaurantId; // không nên
    private Long reservationStatusId;
    private Long userId; // không nên
}
