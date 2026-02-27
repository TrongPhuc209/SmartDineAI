package com.SmartDineAI.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantResponse {

    private String name;
    private String address;
    private String phoneNumber;
    private String decription;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isActive;
    private LocalDateTime createAt;
}