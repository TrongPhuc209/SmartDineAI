package com.SmartDineAI.mapper;

import com.SmartDineAI.dto.review.ReviewResponse;
import com.SmartDineAI.entity.Review;

public class ReviewMapper {

    public static ReviewResponse toResponse(Review review) {

        return ReviewResponse.builder()
                .id(review.getId())
                .customerName(
                        review.getReservation()
                                .getCustomer()
                                .getFullName()
                )
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }

}