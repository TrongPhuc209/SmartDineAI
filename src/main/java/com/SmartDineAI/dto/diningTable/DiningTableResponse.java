package com.SmartDineAI.dto.diningTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiningTableResponse {
    private Long id;
    private String tableCode;
    private int capacity;
    private boolean active;
    private String location;
    private String restaurantName;
}
