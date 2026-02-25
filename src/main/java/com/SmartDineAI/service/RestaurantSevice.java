package com.SmartDineAI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.repository.RestaurantRepository;

@Service
public class RestaurantSevice {
    @Autowired
    private RestaurantRepository restaurantRepository;

    
}
