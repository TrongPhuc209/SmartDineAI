package com.SmartDineAI.service.impl;

import com.SmartDineAI.dto.foodCategory.FoodCategoryCreateRequest;
import com.SmartDineAI.dto.foodCategory.FoodCategoryResponse;
import com.SmartDineAI.dto.foodCategory.FoodCategoryUpdateRequest;
import com.SmartDineAI.entity.FoodCategory;
import com.SmartDineAI.entity.Restaurant;
import com.SmartDineAI.mapper.FoodCategoryMapper;
import com.SmartDineAI.repository.FoodCategoryRepository;
import com.SmartDineAI.repository.RestaurantRepository;
import com.SmartDineAI.service.FoodCategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodCategoryMapper categoryMapper;

    @Override
    public FoodCategoryResponse create(FoodCategoryCreateRequest request) {

        FoodCategory category = categoryMapper.toEntity(request);

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        category.setRestaurant(restaurant);

        categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Override
    public FoodCategoryResponse update(Long id, FoodCategoryUpdateRequest request) {

        FoodCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryMapper.updateEntity(category, request);

        categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Override
    public void delete(Long id) {

        FoodCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }

    @Override
    public FoodCategoryResponse getById(Long id) {

        FoodCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return categoryMapper.toResponse(category);
    }

    @Override
    public List<FoodCategoryResponse> getAll() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public List<FoodCategoryResponse> getByRestaurant(Long restaurantId) {

        return categoryRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}