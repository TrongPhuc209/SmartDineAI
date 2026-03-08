package com.SmartDineAI.dto.diningTable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiningTableRequest {
    private String tableCode;
    private Integer capacity;
    private String location;
    private Long restaurantId;
}
