package com.SmartDineAI.service.impl;

import com.SmartDineAI.dto.review.CreateReviewRequest;
import com.SmartDineAI.dto.review.RatingDistributionResponse;
import com.SmartDineAI.dto.review.RestaurantRatingResponse;
import com.SmartDineAI.dto.review.ReviewResponse;
import com.SmartDineAI.entity.Customer;
import com.SmartDineAI.entity.Reservation;
import com.SmartDineAI.entity.Restaurant;
import com.SmartDineAI.entity.Review;
import com.SmartDineAI.entity.User;
import com.SmartDineAI.mapper.ReviewMapper;
import com.SmartDineAI.repository.CustomerRepository;
import com.SmartDineAI.repository.ReservationRepository;
import com.SmartDineAI.repository.RestaurantRepository;
import com.SmartDineAI.repository.ReviewRepository;
import com.SmartDineAI.repository.UserRepository;
import com.SmartDineAI.service.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public ReviewResponse createReview(CreateReviewRequest request) {

        User user = getCurrentUser();

        Reservation reservation =
                reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if(!reservation.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You cannot review this reservation");
        }

        if(reviewRepository.existsByReservationId(reservation.getId())){
            throw new RuntimeException("Reservation already reviewed");
        }

        Review review = Review.builder()
                .reservation(reservation)
                .restaurant(reservation.getRestaurant())
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);

        return ReviewMapper.toResponse(review);
    }

    @Override
    public List<ReviewResponse> getReviewsByRestaurant(Long restaurantId) {

        return reviewRepository
                .findByRestaurantId(restaurantId)
                .stream()
                .map(ReviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    
    @Override
    public List<ReviewResponse> getMyReviews() {
    User user = getCurrentUser();
    List<Review> reviews =
        reviewRepository.findByReservation_User_Id(user.getId());
    return reviews.stream()
            .map(ReviewMapper::toResponse)
            .collect(Collectors.toList());
    }


    @Override
    public List<ReviewResponse> getAllReviews() {

        return reviewRepository.findAll()
                .stream()
                .map(ReviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId) {

        reviewRepository.deleteById(reviewId);
    }

    @Override
    public boolean canReview(Long reservationId) {

        // lấy user hiện tại từ token
        User currentUser = getCurrentUser();

        // lấy reservation
        Reservation reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // kiểm tra reservation có thuộc user hiện tại không
        if(!reservation.getUser().getId().equals(currentUser.getId())){
            return false;
        }

        if(!reservation.getReservationStatus().getStatusName().equals("COMPLETED")){
            return false;
        }

        // kiểm tra đã có review cho reservation này chưa
        boolean reviewExists = reviewRepository.existsByReservationId(reservationId);

        // nếu chưa có review thì được phép review
        return !reviewExists;
    }

    @Override
    public RestaurantRatingResponse getRestaurantRating(Long restaurantId) {

    Double avg = reviewRepository.getAverageRating(restaurantId);

    long total = reviewRepository.countByRestaurantId(restaurantId);

    return RestaurantRatingResponse.builder()
            .averageRating(avg == null ? 0 : avg)
            .totalReviews(total)
            .build();
    }

    @Override
    public RatingDistributionResponse getRatingDistribution(Long restaurantId) {

        return RatingDistributionResponse.builder()
                .fiveStar(reviewRepository.countByRestaurantIdAndRating(restaurantId,5))
                .fourStar(reviewRepository.countByRestaurantIdAndRating(restaurantId,4))
                .threeStar(reviewRepository.countByRestaurantIdAndRating(restaurantId,3))
                .twoStar(reviewRepository.countByRestaurantIdAndRating(restaurantId,2))
                .oneStar(reviewRepository.countByRestaurantIdAndRating(restaurantId,1))
                .build();
    }

    @Override
    public List<ReviewResponse> getReviewsByRestaurantNewest(Long restaurantId) {

        return reviewRepository
                .findByRestaurantIdOrderByCreatedAtDesc(restaurantId)
                .stream()
                .map(ReviewMapper::toResponse)
                .toList();
    }

    @Override
    public List<ReviewResponse> getReviewsByRestaurantOldest(Long restaurantId) {

        return reviewRepository
                .findByRestaurantIdOrderByCreatedAtAsc(restaurantId)
                .stream()
                .map(ReviewMapper::toResponse)
                .toList();
    }
}       