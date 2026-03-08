package com.SmartDineAI.dto.restaurant;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
public class RestaurantResponse {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String decription;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean active;
    private LocalDateTime createAt;
    private String imageUrl;
}
