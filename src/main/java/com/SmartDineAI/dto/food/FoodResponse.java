package com.SmartDineAI.dto.food;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.SmartDineAI.dto.foodImage.FoodImageResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponse {

    private Long id;

    private Long restaurantId;

    private Long categoryId;

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean isAvailable;

    private LocalDateTime createdAt;

    private List<FoodImageResponse> images;
}