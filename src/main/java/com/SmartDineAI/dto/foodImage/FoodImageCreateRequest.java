package com.SmartDineAI.dto.foodImage;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodImageCreateRequest {

    private Long foodId;

    private String imageUrl;

    private Boolean isMain;

}