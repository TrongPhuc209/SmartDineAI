package com.SmartDineAI.service;

import java.util.*;
import java.util.regex.*;

import org.springframework.stereotype.Component;

import com.SmartDineAI.dto.ai.ParsedRequest;

@Component
public class MessageParser {

    private static final List<String> RESTAURANTS = List.of(
            "Phở Hà Nội",
            "Burger Factory",
            "Đặc sản miền tây",
            "Đặc sản 3 miền",
            "Sushi Tokyo",
            "BBQ House",
            "Pizza Italia",
            "Lẩu Phố",
            "Cafe Chill",
            "Hải sản Biển Đông",
            "Bún Đậu Mắm Tôm",
            "Cơm Tấm Sài Gòn",
            "Bò Né 3 Ngon",
            "Hotpot Paradise",
            "Steak House",
            "Gà Rán Crunchy",
            "Nhà hàng Chay An Lạc",
            "Bánh Mì Corner"
    );


    public ParsedRequest parse(String message){

        ParsedRequest request = new ParsedRequest();

        request.setGuestCount(extractGuestCount(message));

        request.setRestaurantName(extractRestaurant(message));

        request.setTime(extractTime(message));

        return request;

    }



    private Integer extractGuestCount(String message){

        Pattern pattern = Pattern.compile("(\\d+)");

        Matcher matcher = pattern.matcher(message);

        if(matcher.find()){
            return Integer.parseInt(matcher.group(1));
        }

        return null;
    }



    private String extractRestaurant(String message){

        message = message.toLowerCase();

        for(String restaurant : RESTAURANTS){

            if(message.contains(restaurant.toLowerCase())){
                return restaurant;
            }

        }

        return null;
    }



    private String extractTime(String message){

        message = message.toLowerCase();

        if(message.contains("tonight")){
            return "tonight";
        }

        if(message.contains("tomorrow")){
            return "tomorrow";
        }

        if(message.contains("today")){
            return "today";
        }

        return null;
    }

}