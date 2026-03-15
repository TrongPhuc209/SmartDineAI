package com.SmartDineAI.service;

import com.SmartDineAI.dto.review.*;

import java.util.List;

public interface ReviewService {
    
    ReviewResponse createReview(CreateReviewRequest request);

    List<ReviewResponse> getReviewsByRestaurant(Long restaurantId);

    List<ReviewResponse> getMyReviews();
    
    List<ReviewResponse> getAllReviews();
    
    void deleteReview(Long reviewId);
    
    boolean canReview(Long restaurantId);

    List<ReviewResponse> getReviewsByRestaurantNewest(Long restaurantId);

    List<ReviewResponse> getReviewsByRestaurantOldest(Long restaurantId);

    RestaurantRatingResponse getRestaurantRating(Long restaurantId);

    RatingDistributionResponse getRatingDistribution(Long restaurantId);
}