package com.SmartDineAI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.SmartDineAI.dto.restaurant.CreateRestaurantRequest;
import com.SmartDineAI.dto.restaurant.RestaurantResponse;
import com.SmartDineAI.entity.Restaurant;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    Restaurant toRestaurant(CreateRestaurantRequest request);
    RestaurantResponse toRestaurantResponse(Restaurant restaurant);
}
