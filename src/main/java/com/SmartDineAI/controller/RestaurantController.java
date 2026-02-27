package com.SmartDineAI.controller;

import java.util.List;

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

import com.SmartDineAI.dto.request.CreateRestaurantRequest;
import com.SmartDineAI.dto.response.ApiResponse;
import com.SmartDineAI.dto.response.RestaurantResponse;
import com.SmartDineAI.entity.Restaurant;
import com.SmartDineAI.service.RestaurantSevice;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/restaurants")
public class RestaurantController {
    @Autowired
    RestaurantSevice restaurantSevice;
    
    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/id/{id}")
    public ApiResponse<RestaurantResponse> getRestaurantById(@PathVariable Long id){
        ApiResponse<RestaurantResponse> response = new ApiResponse<>();
        response.setResult(restaurantSevice.getRestauranById(id));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<List<RestaurantResponse>> getAllRestaurant(){
        ApiResponse<List<RestaurantResponse>> response = new ApiResponse<>();
        response.setResult(restaurantSevice.getAllRestaurant());
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<RestaurantResponse>> getRestaurantBy(@RequestParam(required = false) String name, 
                                                                    @RequestParam(required = false) Boolean isActive){
        ApiResponse<List<RestaurantResponse>> response = new ApiResponse<>();
        response.setResult(restaurantSevice.getRestaurantBy(name, isActive));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<Restaurant> createRestaurant(@RequestBody @Valid CreateRestaurantRequest request){
        ApiResponse<Restaurant> apiResponse = new ApiResponse<>();
        apiResponse.setResult(restaurantSevice.createRestaurant(request));
        return apiResponse;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ApiResponse<RestaurantResponse> updateRestaurant(@RequestBody @Valid CreateRestaurantRequest request, @PathVariable Long id){
        ApiResponse<RestaurantResponse> response = new ApiResponse<>();
        response.setResult(restaurantSevice.updateRestaurant(request, id));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id){
        restaurantSevice.deleteRestaurant(id);
    }
}
