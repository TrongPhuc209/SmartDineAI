package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartDineAI.dto.diningTable.CreateDiningTableRequest;
import com.SmartDineAI.dto.diningTable.DiningTableResponse;
import com.SmartDineAI.dto.diningTable.UpdateDiningTableRequest;
import com.SmartDineAI.entity.DiningTable;
import com.SmartDineAI.entity.Restaurant;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.mapper.DiningTableMapper;
import com.SmartDineAI.repository.DiningTableRepository;
import com.SmartDineAI.repository.RestaurantRepository;

@Service
@Transactional
public class DiningTableService {
    @Autowired
    private DiningTableRepository diningTableRepository;
    @Autowired
    private DiningTableMapper diningTableMapper;
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    public DiningTableResponse createDiningTable(CreateDiningTableRequest request){
        DiningTable diningTable = diningTableMapper.toDiningTable(request);
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                                                    .orElseThrow(() -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND));
        diningTable.setRestaurant(restaurant);
        diningTableRepository.save(diningTable);
        return diningTableMapper.toResponse(diningTable);
    }

    public DiningTableResponse getDiningTableById(Long id){
        DiningTable diningTable = diningTableRepository.findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        return diningTableMapper.toResponse(diningTable);
    }

    public List<DiningTableResponse> getAllDiningTable(){
        return diningTableRepository.findAll()
                .stream()
                .map(diningTableMapper::toResponse)
                .toList();
    }
    
    public DiningTableResponse updateDiningTable(Long id, UpdateDiningTableRequest request){
        DiningTable diningTable = diningTableRepository.findById(id)
                                                        .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND));
        diningTableMapper.updateDiningTable(diningTable, request);
        return diningTableMapper.toResponse(diningTable);
    }

    public void deleteDiningTable(Long id){
        DiningTable diningTable = diningTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND));

        diningTableRepository.delete(diningTable);
    }

    public List<DiningTableResponse> searchDiningTable(String tableCode, Integer capacity, Boolean active, String location){
        List<DiningTable> responses = diningTableRepository.searchDiningTable(tableCode, capacity, active, location);
        return responses.stream()
                        .map(diningTableMapper::toResponse)
                        .toList();
    }
}
