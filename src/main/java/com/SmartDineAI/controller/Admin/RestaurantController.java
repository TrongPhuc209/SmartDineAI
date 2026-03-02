package com.SmartDineAI.controller.Admin;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.dto.restaurant.CreateRestaurantRequest;
import com.SmartDineAI.dto.restaurant.RestaurantResponse;
import com.SmartDineAI.dto.restaurant.UpdateRestaurantRequest;
import com.SmartDineAI.entity.Restaurant;
import com.SmartDineAI.service.RestaurantSevice;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/restaurants")
public class RestaurantController {
    @Autowired
    RestaurantSevice restaurantSevice;
    
    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<RestaurantResponse> getRestaurantById(@PathVariable Long id){
        ApiResponse<RestaurantResponse> response = new ApiResponse<>();
        response.setResult(restaurantSevice.getRestauranById(id));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<Page<RestaurantResponse>> getAllRestaurant(Pageable pageable){
        Page<RestaurantResponse> result = restaurantSevice.getAllRestaurant(pageable);
        return ApiResponse.<Page<RestaurantResponse>>builder()
                .result(result)
                .build();
    }
    
    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<Restaurant> createRestaurant(@RequestBody @Valid CreateRestaurantRequest request){
        ApiResponse<Restaurant> apiResponse = new ApiResponse<>();
        apiResponse.setResult(restaurantSevice.createRestaurant(request));
        return apiResponse;
    }
    
    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<RestaurantResponse> updateRestaurant(@RequestBody @Valid UpdateRestaurantRequest request, @PathVariable Long id){
        ApiResponse<RestaurantResponse> response = new ApiResponse<>();
        response.setResult(restaurantSevice.updateRestaurant(request, id));
        return response;
    }
    
    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id){
        restaurantSevice.deleteRestaurant(id);
    }

    
    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<RestaurantResponse>> searchRestaurant(@RequestParam(required = false) String name, 
                                                                    @RequestParam(required = false) Boolean active){
        ApiResponse<List<RestaurantResponse>> response = new ApiResponse<>();
        response.setResult(restaurantSevice.searchRestaurant(name, active));
        return response;
    }
}
