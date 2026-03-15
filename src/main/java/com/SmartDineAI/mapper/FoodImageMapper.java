package com.SmartDineAI.mapper;


import com.SmartDineAI.dto.foodImage.FoodImageCreateRequest;
import com.SmartDineAI.dto.foodImage.FoodImageResponse;
import com.SmartDineAI.dto.foodImage.FoodImageUpdateRequest;
import com.SmartDineAI.entity.FoodImage;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FoodImageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "food", ignore = true)
    FoodImage toEntity(FoodImageCreateRequest request);
    
    @Mapping(target = "foodId", source = "food.id")
    FoodImageResponse toResponse(FoodImage entity);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "food", ignore = true)
    void updateEntity(@MappingTarget FoodImage entity, FoodImageUpdateRequest request);
}