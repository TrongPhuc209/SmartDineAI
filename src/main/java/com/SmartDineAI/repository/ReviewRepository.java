package com.SmartDineAI.repository;

import com.SmartDineAI.entity.Review;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReservationId(Long reservationId);

    boolean existsByReservationId(Long reservationId);

    List<Review> findByRestaurantId(Long restaurantId);
    List<Review> findByReservation_User_Id(Long userId);

    // sort theo thời gian
    List<Review> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);

    List<Review> findByRestaurantIdOrderByCreatedAtAsc(Long restaurantId);

    // trung bình rating
    @Query("""
        SELECT AVG(r.rating)
        FROM Review r
        WHERE r.restaurant.id = :restaurantId
    """)
    Double getAverageRating(Long restaurantId);

    // tổng số review
    long countByRestaurantId(Long restaurantId);

    // thống kê số sao
    long countByRestaurantIdAndRating(Long restaurantId, Integer rating);
}