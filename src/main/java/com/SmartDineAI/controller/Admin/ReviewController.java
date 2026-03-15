package com.SmartDineAI.controller.admin;


import com.SmartDineAI.dto.review.CreateReviewRequest;
import com.SmartDineAI.dto.review.RatingDistributionResponse;
import com.SmartDineAI.dto.review.RestaurantRatingResponse;
import com.SmartDineAI.dto.review.ReviewResponse;
import com.SmartDineAI.service.ReviewService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    // Create review
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ReviewResponse createReview(
            @RequestBody CreateReviewRequest request
    ){
        return reviewService.createReview(request);
    }
    // Get reviews of a restaurant
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurant/{restaurantId}")
    public List<ReviewResponse> getReviewsByRestaurant(
            @PathVariable Long restaurantId
    ){
        return reviewService.getReviewsByRestaurant(restaurantId);
    }
    // Get my reviews
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/my")
    public List<ReviewResponse> getMyReviews(){
        return reviewService.getMyReviews();
    }
    // Check if current user can review a restaurant
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/can-review/{reservationId}")
    public Boolean canReview(
            @PathVariable Long reservationId
    ){
        return reviewService.canReview(reservationId);
    }
    // RATING SUMMARY
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurant/{restaurantId}/rating")
    public RestaurantRatingResponse getRestaurantRating(
            @PathVariable Long restaurantId){
        return reviewService.getRestaurantRating(restaurantId);
    }
    // RATING DISTRIBUTION
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurant/{restaurantId}/distribution")
    public RatingDistributionResponse getRatingDistribution(
            @PathVariable Long restaurantId){
        return reviewService.getRatingDistribution(restaurantId);
    }
    // SORT NEWEST
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurant/{restaurantId}/newest")
    public List<ReviewResponse> getReviewsNewest(
            @PathVariable Long restaurantId){
        return reviewService.getReviewsByRestaurantNewest(restaurantId);
    }
    // SORT OLDEST
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/restaurant/{restaurantId}/oldest")
    public List<ReviewResponse> getReviewsOldest(
            @PathVariable Long restaurantId){
        return reviewService.getReviewsByRestaurantOldest(restaurantId);
    }


    // Get all reviews
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ReviewResponse> getAllReviews(){
        return reviewService.getAllReviews();
    }
    // Delete review
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{reviewId}")
    public void deleteReview(
            @PathVariable Long reviewId
    ){
        reviewService.deleteReview(reviewId);
    }

}