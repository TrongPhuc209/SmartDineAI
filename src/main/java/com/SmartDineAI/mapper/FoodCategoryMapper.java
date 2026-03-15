package com.SmartDineAI.mapper;


import com.SmartDineAI.dto.foodCategory.FoodCategoryCreateRequest;
import com.SmartDineAI.dto.foodCategory.FoodCategoryResponse;
import com.SmartDineAI.dto.foodCategory.FoodCategoryUpdateRequest;
import com.SmartDineAI.entity.FoodCategory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FoodCategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "foods", ignore = true)
    FoodCategory toEntity(FoodCategoryCreateRequest request);
    
    @Mapping(target = "restaurantId", source = "restaurant.id")
    FoodCategoryResponse toResponse(FoodCategory entity);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "foods", ignore = true)
    void updateEntity(@MappingTarget FoodCategory entity, FoodCategoryUpdateRequest request);
}