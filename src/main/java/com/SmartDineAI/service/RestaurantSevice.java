package com.SmartDineAI.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.restaurant.CreateRestaurantRequest;
import com.SmartDineAI.dto.restaurant.RestaurantResponse;
import com.SmartDineAI.dto.restaurant.UpdateRestaurantRequest;
import com.SmartDineAI.entity.Restaurant;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.mapper.RestaurantMapper;
import com.SmartDineAI.repository.RestaurantRepository;

@Service
public class RestaurantSevice {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantMapper restaurantMapper;

    public RestaurantResponse getRestauranById(Long Id){
        Restaurant restaurant = restaurantRepository.findById(Id)
                                                    .orElseThrow(() -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND));
        return restaurantMapper.toRestaurantResponse(restaurant);
    }

    public Page<RestaurantResponse> getAllRestaurant(Pageable pageable){
        return restaurantRepository.findAll(pageable)
                .map(restaurantMapper::toRestaurantResponse);
    }

    public Restaurant createRestaurant(CreateRestaurantRequest request){
        if(restaurantRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.RESTAURANT_NAME_ALREADY_EXISTS);
        }
        Restaurant restaurant = restaurantMapper.toRestaurant(request);
        return restaurantRepository.save(restaurant);
    }

    public RestaurantResponse updateRestaurant(UpdateRestaurantRequest request, Long id){
        if(restaurantRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.RESTAURANT_NAME_ALREADY_EXISTS);
        }
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        restaurantMapper.updateRestaurant(restaurant, request);
        return restaurantMapper.toRestaurantResponse(restaurant);
    }

    public void deleteRestaurant(Long id){
        restaurantRepository.deleteById(id);
    }

    public List<RestaurantResponse> searchRestaurant(String name, Boolean isActive){
        List<Restaurant> restaurants = restaurantRepository.searchRestaurant(name, isActive);

        return restaurants.stream()
                .map(restaurantMapper::toRestaurantResponse)
                .toList();
    }
}
