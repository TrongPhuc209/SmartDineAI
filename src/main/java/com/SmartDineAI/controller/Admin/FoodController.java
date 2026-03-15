package com.SmartDineAI.controller.admin;


import com.SmartDineAI.dto.food.FoodCreateRequest;
import com.SmartDineAI.dto.food.FoodResponse;
import com.SmartDineAI.dto.food.FoodUpdateRequest;
import com.SmartDineAI.service.FoodService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public FoodResponse create(@RequestBody FoodCreateRequest request) {
        return foodService.create(request);
    }

    @PutMapping("/{id}")
    public FoodResponse update(
            @PathVariable Long id,
            @RequestBody FoodUpdateRequest request
    ) {
        return foodService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        foodService.delete(id);
    }

    @GetMapping
    public List<FoodResponse> getAll() {
        return foodService.getAll();
    }

    @GetMapping("/{id}")
    public FoodResponse getById(@PathVariable Long id) {
        return foodService.getById(id);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<FoodResponse> getByRestaurant(@PathVariable Long restaurantId) {
        return foodService.getByRestaurant(restaurantId);
    }

    @GetMapping("/category/{categoryId}")
    public List<FoodResponse> getByCategory(@PathVariable Long categoryId) {
        return foodService.getByCategory(categoryId);
    }

    @GetMapping("/search")
    public List<FoodResponse> search(@RequestParam String name) {
        return foodService.searchByName(name);
    }
}