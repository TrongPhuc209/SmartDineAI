package com.SmartDineAI.dto.restaurant;

import java.time.LocalTime;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
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
public class UpdateRestaurantRequest {
    @Size(max = 50, message = "INVALID_FORMAT_RESTAURANT_NAME")
    private String name;

    @Size(min = 10, max = 100, message = "INVALID_FORMAT_RESTAURANT_ADDRESS")
    private String address;

    @Digits(integer = 15, fraction = 0, message = "INVALID_FORMAT_RESTAURANT_PHONE_NUMBER")
    private String phoneNumber;

    private String decription;

    private LocalTime openTime;

    private LocalTime closeTime;

    private Boolean active;

    private String imageUrl;
}
