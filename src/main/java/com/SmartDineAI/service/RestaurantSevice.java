package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.request.CreateRestaurantRequest;
import com.SmartDineAI.dto.response.RestaurantResponse;
import com.SmartDineAI.entity.Restaurant;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.repository.RestaurantRepository;

@Service
public class RestaurantSevice {
    @Autowired
    private RestaurantRepository restaurantRepository;

    private RestaurantResponse mapToRestaurantResponse(Restaurant restaurant){
        RestaurantResponse RR = new RestaurantResponse();

        RR.setName(restaurant.getName());
        RR.setAddress(restaurant.getAddress());
        RR.setPhoneNumber(restaurant.getPhoneNumber());
        RR.setDecription(restaurant.getDecription());
        RR.setOpenTime(restaurant.getOpenTime());
        RR.setCloseTime(restaurant.getCloseTime());

        return RR;
    }

    public RestaurantResponse getRestauranById(Long Id){
        Restaurant restaurant = restaurantRepository.findById(Id)
                                                    .orElseThrow(() -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND));
        return mapToRestaurantResponse(restaurant);
    }

    public List<RestaurantResponse> getAllRestaurant(){
        return restaurantRepository.findAll().stream().map(this::mapToRestaurantResponse).toList();
    }

    public List<RestaurantResponse> getRestaurantBy(String name, Boolean isActive){
        return restaurantRepository.findAllByName(name)
                                    .stream()
                                    .map(this::mapToRestaurantResponse)
                                    .toList();
                                                                
    }

    public Restaurant createRestaurant(CreateRestaurantRequest request){
        if(restaurantRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.RESTAURANT_NAME_ALREADY_EXISTS);
        }

        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhoneNumber(request.getPhoneNumber());
        restaurant.setDecription(request.getDecription());
        restaurant.setOpenTime(request.getOpenTime());
        restaurant.setCloseTime(request.getCloseTime());

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
        return mapToRestaurantResponse(restaurantRepository.findById(id).orElseThrow());
        // Trong dto chưa thêm CreateAt và isActice nên update sẽ bị null
    }

    public void deleteRestaurant(Long id){
        restaurantRepository.deleteById(id);
    }
}
