package com.SmartDineAI.dto.review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingDistributionResponse {
    private long fiveStar;
    private long fourStar;
    private long threeStar;
    private long twoStar;
    private long oneStar;
}
