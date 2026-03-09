package com.SmartDineAI.dto.ai;

import lombok.Data;

@Data
public class ParsedRequest {
    private Integer guestCount;

    private String restaurantName;

    private String time;
    
}
