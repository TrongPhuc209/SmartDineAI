package com.SmartDineAI.dto.foodCategory;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodCategoryUpdateRequest {

    private String name;

    private String description;

}