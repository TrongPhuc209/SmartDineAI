package com.SmartDineAI.service.impl;


import com.SmartDineAI.dto.food.FoodCreateRequest;
import com.SmartDineAI.dto.food.FoodResponse;
import com.SmartDineAI.dto.food.FoodUpdateRequest;
import com.SmartDineAI.dto.foodImage.FoodImageResponse;
import com.SmartDineAI.entity.Food;
import com.SmartDineAI.entity.FoodCategory;
import com.SmartDineAI.entity.Restaurant;
import com.SmartDineAI.mapper.FoodMapper;
import com.SmartDineAI.mapper.FoodImageMapper;
import com.SmartDineAI.repository.FoodRepository;
import com.SmartDineAI.repository.FoodImageRepository;
import com.SmartDineAI.repository.FoodCategoryRepository;
import com.SmartDineAI.repository.RestaurantRepository;
import com.SmartDineAI.service.FoodService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodCategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodImageRepository foodImageRepository;

    private final FoodMapper foodMapper;
    private final FoodImageMapper foodImageMapper;

    @Override
    public FoodResponse create(FoodCreateRequest request) {

        Food food = foodMapper.toEntity(request);

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        FoodCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        food.setRestaurant(restaurant);
        food.setCategory(category);
        food.setCreatedAt(LocalDateTime.now());

        foodRepository.save(food);

        return foodMapper.toResponse(food);
    }

    @Override
    public FoodResponse update(Long id, FoodUpdateRequest request) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        if (request.getCategoryId() != null) {
            FoodCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            food.setCategory(category);
        }

        foodMapper.updateEntity(food, request);

        foodRepository.save(food);

        return foodMapper.toResponse(food);
    }

    @Override
    public void delete(Long id) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        foodRepository.delete(food);
    }

    @Override
    public FoodResponse getById(Long id) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        FoodResponse response = foodMapper.toResponse(food);

        List<FoodImageResponse> images = foodImageRepository.findByFoodId(id)
                .stream()
                .map(foodImageMapper::toResponse)
                .toList();

        response.setImages(images);

        return response;
    }

    @Override
    public List<FoodResponse> getAll() {

        return foodRepository.findAll()
                .stream()
                .map(foodMapper::toResponse)
                .toList();
    }

    @Override
    public List<FoodResponse> getByRestaurant(Long restaurantId) {

        return foodRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(foodMapper::toResponse)
                .toList();
    }

    @Override
    public List<FoodResponse> getByCategory(Long categoryId) {

        return foodRepository.findByCategoryId(categoryId)
                .stream()
                .map(foodMapper::toResponse)
                .toList();
    }

    @Override
    public List<FoodResponse> searchByName(String name) {

        return foodRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(foodMapper::toResponse)
                .toList();
    }

    @Override
    public List<FoodResponse> searchByNameAndRestaurant(String name, Long restaurantId) {

        return foodRepository.searchFoodByNameAndRestaurant(name, restaurantId)
                            .stream()
                            .map(foodMapper::toResponse)
                            .toList();

    }
}