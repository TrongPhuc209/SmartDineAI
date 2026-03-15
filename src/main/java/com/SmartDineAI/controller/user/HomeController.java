package com.SmartDineAI.controller.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.dto.diningTable.DiningTableResponse;
import com.SmartDineAI.dto.food.FoodResponse;
import com.SmartDineAI.dto.foodCategory.FoodCategoryResponse;
import com.SmartDineAI.dto.reservation.CreateReservationRequest;
import com.SmartDineAI.dto.reservation.ReservationResponse;
import com.SmartDineAI.dto.restaurant.RestaurantResponse;
import com.SmartDineAI.dto.review.CreateReviewRequest;
import com.SmartDineAI.dto.review.RatingDistributionResponse;
import com.SmartDineAI.dto.review.RestaurantRatingResponse;
import com.SmartDineAI.dto.review.ReviewResponse;
import com.SmartDineAI.dto.user.UserResponse;
import com.SmartDineAI.service.DiningTableService;
import com.SmartDineAI.service.FoodCategoryService;
import com.SmartDineAI.service.FoodService;
import com.SmartDineAI.service.ReservationService;
import com.SmartDineAI.service.RestaurantSevice;
import com.SmartDineAI.service.ReviewService;
import com.SmartDineAI.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private DiningTableService diningTableService;
    @Autowired
    private RestaurantSevice restaurantSevice;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private FoodCategoryService foodCategoryService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/restaurants")
    public ApiResponse<Page<RestaurantResponse>> searchRestaurant(@RequestParam(required = false) String name, 
                                                                    @RequestParam(required = false) Boolean active,
                                                                    Pageable pageable){
        ApiResponse<Page<RestaurantResponse>> response = new ApiResponse<>();
        response.setResult(restaurantSevice.searchRestaurant(name, active = true, pageable));
        return response;
    }

    @PostMapping("/bookings/{restaurantId}")
    public ApiResponse<ReservationResponse> createReservation(@RequestBody @Valid CreateReservationRequest request, @PathVariable Long restaurantId){
        ApiResponse<ReservationResponse> response = new ApiResponse<>();
        response.setResult(reservationService.createReservationById(request, restaurantId));
        return response;
    }


    @GetMapping("/dining-tables")
    public ApiResponse<Page<DiningTableResponse>> searchDiningTable(
            @RequestParam(required = false) String tableCode,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long restaurantId,
            Pageable pageable) {

        ApiResponse<Page<DiningTableResponse>> response = new ApiResponse<>();
        response.setResult(
                diningTableService.searchDiningTable(
                        tableCode,
                        capacity,
                        active,
                        location,
                        restaurantId,
                        pageable
                )
        );
        return response;
    }

    @GetMapping("/available-tables")
    public ApiResponse<List<DiningTableResponse>> getAvailableTables(
            @RequestParam Long restaurantId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime,
            @RequestParam Integer guestCount
    ) {

        ApiResponse<List<DiningTableResponse>> response = new ApiResponse<>();

        response.setResult(
                diningTableService.getAvailableTables(
                        restaurantId,
                        startTime,
                        endTime,
                        guestCount
                )
        );

        return response;
    }

    @GetMapping("/my-reservation")
    public ApiResponse<Page<ReservationResponse>> myReservation(Pageable pageable){
        return ApiResponse.<Page<ReservationResponse>>builder()
                                                    .result(reservationService
                                                            .myReservation(pageable))
                                                    .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo(){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getMyInfo());
        return response;
    }

    @DeleteMapping("/my/{id}")
    public void myDelete(@PathVariable Long id){
        reservationService.myDelete(id);
    }

    @GetMapping("/restaurants/{id}")
    public ApiResponse<RestaurantResponse> getRestaurantById(@PathVariable Long id){
        ApiResponse<RestaurantResponse> response = new ApiResponse<>();
        response.setResult(restaurantSevice.getRestauranById(id));
        return response;
    }

    @GetMapping("/food/search")
    public List<FoodResponse> search(
            @RequestParam String name,
            @RequestParam Long restaurantId) {

        return foodService.searchByNameAndRestaurant(name, restaurantId);
    }

    @GetMapping("/restaurants/category/{restaurantId}")
    public List<FoodCategoryResponse> getByRestaurant(@PathVariable Long restaurantId) {
        return foodCategoryService.getByRestaurant(restaurantId);
    }

    @GetMapping("/category/{categoryId}")
    public List<FoodResponse> getByCategory(@PathVariable Long categoryId) {
        return foodService.getByCategory(categoryId);
    }

    @GetMapping("/reviews/can-review/{reservationId}")
    public Boolean canReview(
            @PathVariable Long reservationId
    ){
        return reviewService.canReview(reservationId);
    }

    @PostMapping("/reviews")
    public ReviewResponse createReview(
            @RequestBody CreateReviewRequest request
    ){
        return reviewService.createReview(request);
    }

    @GetMapping("/reviews/restaurant/{restaurantId}/rating")
    public RestaurantRatingResponse getRestaurantRating(
            @PathVariable Long restaurantId){
        return reviewService.getRestaurantRating(restaurantId);
    }

    @GetMapping("/reviews/restaurant/{restaurantId}")
    public List<ReviewResponse> getReviewsByRestaurant(
            @PathVariable Long restaurantId
    ){
        return reviewService.getReviewsByRestaurant(restaurantId);
    }

    @GetMapping("/reviews/restaurant/{restaurantId}/distribution")
    public RatingDistributionResponse getRatingDistribution(
            @PathVariable Long restaurantId){
        return reviewService.getRatingDistribution(restaurantId);
    }
}
