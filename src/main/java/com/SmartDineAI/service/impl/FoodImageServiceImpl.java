package com.SmartDineAI.service.impl;

import com.SmartDineAI.dto.foodImage.FoodImageCreateRequest;
import com.SmartDineAI.dto.foodImage.FoodImageResponse;
import com.SmartDineAI.dto.foodImage.FoodImageUpdateRequest;
import com.SmartDineAI.entity.Food;
import com.SmartDineAI.entity.FoodImage;
import com.SmartDineAI.mapper.FoodImageMapper;
import com.SmartDineAI.repository.FoodRepository;
import com.SmartDineAI.repository.FoodImageRepository;
import com.SmartDineAI.service.FoodImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodImageServiceImpl implements FoodImageService {

    private final FoodImageRepository foodImageRepository;
    private final FoodRepository foodRepository;
    private final FoodImageMapper foodImageMapper;

    @Override
    public FoodImageResponse create(FoodImageCreateRequest request) {

        FoodImage image = foodImageMapper.toEntity(request);

        Food food = foodRepository.findById(request.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        image.setFood(food);

        foodImageRepository.save(image);

        return foodImageMapper.toResponse(image);
    }

    @Override
    public FoodImageResponse update(Long id, FoodImageUpdateRequest request) {

        FoodImage image = foodImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        foodImageMapper.updateEntity(image, request);

        foodImageRepository.save(image);

        return foodImageMapper.toResponse(image);
    }

    @Override
    public void delete(Long id) {

        FoodImage image = foodImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        foodImageRepository.delete(image);
    }

    @Override
    public FoodImageResponse getById(Long id) {

        FoodImage image = foodImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        return foodImageMapper.toResponse(image);
    }

    @Override
    public List<FoodImageResponse> getByFood(Long foodId) {

        return foodImageRepository.findByFoodId(foodId)
                .stream()
                .map(foodImageMapper::toResponse)
                .toList();
    }
}