package com.SmartDineAI.dto.food;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodCreateRequest {

    private Long restaurantId;

    private Long categoryId;

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean isAvailable;

}