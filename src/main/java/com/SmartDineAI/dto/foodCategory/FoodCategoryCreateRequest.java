package com.SmartDineAI.dto.foodCategory;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodCategoryCreateRequest {

    private Long restaurantId;

    private String name;

    private String description;

}