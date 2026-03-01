package com.SmartDineAI.dto.diningTable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDiningTableRequest {
    private String tableCode;
    private int capacity;
    private String location;
    private Long restaurantId;
}
