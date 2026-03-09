package com.SmartDineAI.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.ai.RecommendationDTO;
import com.SmartDineAI.entity.Reservation;
import com.SmartDineAI.repository.ReservationRepository;

@Service
public class RecommendationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<RecommendationDTO> getRecommendations(String username){

        List<Reservation> reservations = reservationRepository.findByUserUsername(username);

        List<RecommendationDTO> result = new ArrayList<>();

        for(Reservation r : reservations){

            String time = r.getStartTime()
                    .toLocalTime()
                    .toString()
                    .substring(0,5);

            result.add(
                    new RecommendationDTO(
                            r.getRestaurant().getId(),
                            r.getRestaurant().getName(),
                            r.getDiningTable().getTableCode(),
                            time
                    )
            );
        }

        return result;

    }

}