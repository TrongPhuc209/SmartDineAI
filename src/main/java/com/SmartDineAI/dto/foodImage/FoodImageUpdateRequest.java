package com.SmartDineAI.dto.foodImage;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodImageUpdateRequest {

    private String imageUrl;

    private Boolean isMain;

}