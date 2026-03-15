package com.SmartDineAI.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantRatingResponse {
    private Double averageRating;
    private Long totalReviews;
}