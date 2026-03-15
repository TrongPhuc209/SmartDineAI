package com.SmartDineAI.controller.admin;

import com.SmartDineAI.dto.foodCategory.FoodCategoryCreateRequest;
import com.SmartDineAI.dto.foodCategory.FoodCategoryResponse;
import com.SmartDineAI.dto.foodCategory.FoodCategoryUpdateRequest;
import com.SmartDineAI.service.FoodCategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/food-categories")
@RequiredArgsConstructor
public class FoodCategoryController {

    private final FoodCategoryService categoryService;

    @PostMapping
    public FoodCategoryResponse create(@RequestBody FoodCategoryCreateRequest request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public FoodCategoryResponse update(
            @PathVariable Long id,
            @RequestBody FoodCategoryUpdateRequest request
    ) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @GetMapping
    public List<FoodCategoryResponse> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public FoodCategoryResponse getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<FoodCategoryResponse> getByRestaurant(@PathVariable Long restaurantId) {
        return categoryService.getByRestaurant(restaurantId);
    }
}