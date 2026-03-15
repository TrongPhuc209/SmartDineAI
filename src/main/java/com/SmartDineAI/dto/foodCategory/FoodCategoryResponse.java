package com.SmartDineAI.dto.foodCategory;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodCategoryResponse {

    private Long id;

    private Long restaurantId;

    private String name;

    private String description;

}