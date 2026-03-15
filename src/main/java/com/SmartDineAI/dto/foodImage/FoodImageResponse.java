package com.SmartDineAI.dto.foodImage;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodImageResponse {
    private Long id;
    private Long foodId;
    private String imageUrl;
    private Boolean isMain;
}