package com.SmartDineAI.dto.review;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private String customerName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}