package com.SmartDineAI.dto.review;

import lombok.Data;

@Data
public class CreateReviewRequest {
    private Long reservationId;
    private Integer rating;
    private String comment;
}