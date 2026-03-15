package com.SmartDineAI.service;


import java.util.List;

import com.SmartDineAI.dto.foodCategory.FoodCategoryCreateRequest;
import com.SmartDineAI.dto.foodCategory.FoodCategoryResponse;
import com.SmartDineAI.dto.foodCategory.FoodCategoryUpdateRequest;

public interface FoodCategoryService {

    FoodCategoryResponse create(FoodCategoryCreateRequest request);

    FoodCategoryResponse update(Long id, FoodCategoryUpdateRequest request);

    void delete(Long id);

    FoodCategoryResponse getById(Long id);

    List<FoodCategoryResponse> getAll();

    List<FoodCategoryResponse> getByRestaurant(Long restaurantId);
}