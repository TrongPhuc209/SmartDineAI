package com.SmartDineAI.dto.food;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodUpdateRequest {

    private Long categoryId;

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean isAvailable;

}