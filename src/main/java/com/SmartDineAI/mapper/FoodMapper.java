package com.SmartDineAI.mapper;

import com.SmartDineAI.dto.food.FoodCreateRequest;
import com.SmartDineAI.dto.food.FoodResponse;
import com.SmartDineAI.dto.food.FoodUpdateRequest;
import com.SmartDineAI.entity.Food;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {FoodImageMapper.class})
public interface FoodMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Food toEntity(FoodCreateRequest request);
    
    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "categoryId", source = "category.id")
    FoodResponse toResponse(Food entity);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Food entity, FoodUpdateRequest request);
}