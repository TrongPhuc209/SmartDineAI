package com.SmartDineAI.controller.admin;

import com.SmartDineAI.dto.foodImage.FoodImageCreateRequest;
import com.SmartDineAI.dto.foodImage.FoodImageResponse;
import com.SmartDineAI.dto.foodImage.FoodImageUpdateRequest;
import com.SmartDineAI.service.FoodImageService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/food-images")
@RequiredArgsConstructor
public class FoodImageController {

    private final FoodImageService foodImageService;

    @PostMapping
    public FoodImageResponse create(@RequestBody FoodImageCreateRequest request) {
        return foodImageService.create(request);
    }

    @PutMapping("/{id}")
    public FoodImageResponse update(
            @PathVariable Long id,
            @RequestBody FoodImageUpdateRequest request
    ) {
        return foodImageService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        foodImageService.delete(id);
    }

    @GetMapping("/{id}")
    public FoodImageResponse getById(@PathVariable Long id) {
        return foodImageService.getById(id);
    }

    @GetMapping("/food/{foodId}")
    public List<FoodImageResponse> getByFood(@PathVariable Long foodId) {
        return foodImageService.getByFood(foodId);
    }
}