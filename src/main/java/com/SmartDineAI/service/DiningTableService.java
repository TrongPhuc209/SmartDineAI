package com.SmartDineAI.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
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
import com.SmartDineAI.repository.ReservationRepository;
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
    @Autowired
    private ReservationRepository reservationRepository;
    
    public DiningTableResponse createDiningTable(CreateDiningTableRequest request){
        DiningTable diningTable = diningTableMapper.toDiningTable(request);
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                                                    .orElseThrow(() -> new AppException(ErrorCode.RESTAURANT_NOT_FOUND));


        diningTable.setRestaurant(restaurant);
        diningTableRepository.save(diningTable);

        System.out.println("Request capacity: " + request.getCapacity());
        System.out.println("Entity capacity before save: " + diningTable.getCapacity());

        return diningTableMapper.toResponse(diningTable);
    }

    public DiningTableResponse getDiningTableById(Long id){
        DiningTable diningTable = diningTableRepository.findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        return diningTableMapper.toResponse(diningTable);
    }

    public Page<DiningTableResponse> getAllDiningTable(Pageable pageable){
        return diningTableRepository.findAll(pageable)
                .map(diningTableMapper::toResponse);
    }
    
    public DiningTableResponse updateDiningTable(Long id, UpdateDiningTableRequest request){
        DiningTable diningTable = diningTableRepository.findById(id)
                                                        .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND));
        diningTableMapper.updateDiningTable(diningTable, request);
        return diningTableMapper.toResponse(diningTable);
    }

    public void updateActive(Long id){
        DiningTable diningTable = diningTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND));

        diningTable.setActive(!diningTable.getActive());
        diningTableRepository.save(diningTable);
    }

    public void deleteDiningTable(Long id){
        DiningTable diningTable = diningTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DINING_TABLE_NOT_FOUND));

        diningTableRepository.delete(diningTable);
    }

    public Page<DiningTableResponse> searchDiningTable(
            String tableCode,
            Integer capacity,
            Boolean active,
            String location,
            Long restaurantId,
            Pageable pageable){

        return diningTableRepository
                .searchDiningTable(tableCode, capacity, active, location, restaurantId, pageable)
                .map(diningTableMapper::toResponse);
    }

    public List<DiningTableResponse> getAvailableTables(
            Long restaurantId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Integer guestCount
    ) {

        List<DiningTable> tables = diningTableRepository
                .findByRestaurantIdAndCapacityGreaterThanEqual(
                        restaurantId,
                        guestCount
                );

        List<DiningTable> availableTables = tables.stream()
                .filter(table -> !reservationRepository.existsOverlap(
                        table.getId(),
                        startTime,
                        endTime
                ))
                .toList();

        return availableTables.stream()
                .map(diningTableMapper::toResponse)
                .toList();
    }
}
