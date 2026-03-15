package com.SmartDineAI.service;


import java.util.List;

import com.SmartDineAI.dto.foodImage.FoodImageCreateRequest;
import com.SmartDineAI.dto.foodImage.FoodImageResponse;
import com.SmartDineAI.dto.foodImage.FoodImageUpdateRequest;

public interface FoodImageService {

    FoodImageResponse create(FoodImageCreateRequest request);

    FoodImageResponse update(Long id, FoodImageUpdateRequest request);

    void delete(Long id);

    FoodImageResponse getById(Long id);

    List<FoodImageResponse> getByFood(Long foodId);

}