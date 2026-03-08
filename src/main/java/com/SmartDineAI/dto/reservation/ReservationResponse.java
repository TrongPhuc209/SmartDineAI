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
public class ReservationResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer guestCount;
    private LocalDateTime createAt;

    private String customerPhoneNumber;
    private String diningTableName;
    private String restaurantName;
    private String reservationStatusName;
    private String userName;
}
