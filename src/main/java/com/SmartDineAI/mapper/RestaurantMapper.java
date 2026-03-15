package com.SmartDineAI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.SmartDineAI.dto.restaurant.CreateRestaurantRequest;
import com.SmartDineAI.dto.restaurant.RestaurantResponse;
import com.SmartDineAI.dto.restaurant.UpdateRestaurantRequest;
import com.SmartDineAI.entity.Restaurant;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestaurantMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "foods", ignore = true)
    Restaurant toRestaurant(CreateRestaurantRequest request);
    
    RestaurantResponse toRestaurantResponse(Restaurant restaurant);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "foods", ignore = true)
    void updateRestaurant(@MappingTarget Restaurant restaurant, UpdateRestaurantRequest request);
}
