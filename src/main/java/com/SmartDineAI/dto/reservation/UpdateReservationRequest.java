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
    private Integer guestCount;

    private Long diningTableId;
    private Long reservationStatusId;
}
