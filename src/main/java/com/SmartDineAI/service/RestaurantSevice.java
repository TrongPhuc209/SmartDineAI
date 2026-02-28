package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.restaurant.CreateRestaurantRequest;
import com.SmartDineAI.dto.restaurant.RestaurantResponse;
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

    public List<RestaurantResponse> getAllRestaurant(){
        return restaurantRepository.findAll()
                                    .stream()
                                    .map(restaurantMapper::toRestaurantResponse)
                                    .toList();
    }
    
    public Restaurant createRestaurant(CreateRestaurantRequest request){
        if(restaurantRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.RESTAURANT_NAME_ALREADY_EXISTS);
        }
        Restaurant restaurant = restaurantMapper.toRestaurant(request);
        return restaurantRepository.save(restaurant);                                                    
    }
    
    public RestaurantResponse updateRestaurant(CreateRestaurantRequest request, Long id){
        if(restaurantRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.RESTAURANT_NAME_ALREADY_EXISTS);
        }
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        

        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhoneNumber(request.getPhoneNumber());
        restaurant.setDecription(request.getDecription());
        restaurant.setOpenTime(request.getOpenTime());
        restaurant.setCloseTime(request.getCloseTime());
        
        restaurantRepository.save(restaurant);
        return restaurantMapper.toRestaurantResponse(restaurant);
        // Trong dto chưa thêm CreateAt và isActice nên update sẽ bị null
    }
    
    public void deleteRestaurant(Long id){
        restaurantRepository.deleteById(id);
    }

    // Search
    public List<RestaurantResponse> searchRestaurant(String name, Boolean isActive){
        List<Restaurant> restaurants =
        restaurantRepository.searchRestaurant(name, isActive);
    
        return restaurants.stream()
                .map(restaurantMapper::toRestaurantResponse)
                .toList();                                       
    }
}
