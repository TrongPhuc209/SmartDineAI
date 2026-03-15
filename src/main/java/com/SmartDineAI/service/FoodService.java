package com.SmartDineAI.service;



import java.util.List;

import com.SmartDineAI.dto.food.FoodCreateRequest;
import com.SmartDineAI.dto.food.FoodResponse;
import com.SmartDineAI.dto.food.FoodUpdateRequest;

public interface FoodService {

    FoodResponse create(FoodCreateRequest request);

    FoodResponse update(Long id, FoodUpdateRequest request);

    void delete(Long id);

    FoodResponse getById(Long id);

    List<FoodResponse> getAll();

    List<FoodResponse> getByRestaurant(Long restaurantId);

    List<FoodResponse> getByCategory(Long categoryId);

    List<FoodResponse> searchByName(String name);

    List<FoodResponse> searchByNameAndRestaurant(String name, Long restaurantId);
}