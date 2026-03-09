package com.SmartDineAI.dto.ai;

import lombok.Data;

@Data
public class RecommendationDTO {

    private Long restaurantId;
    private String restaurantName;
    private String tableCode;
    private String time;

    public RecommendationDTO(Long restaurantId, String restaurantName, String tableCode, String time) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.tableCode = tableCode;
        this.time = time;
    }

}
