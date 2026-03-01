package com.SmartDineAI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.SmartDineAI.dto.reservation.CreateReservationRequest;
import com.SmartDineAI.dto.reservation.ReservationResponse;
import com.SmartDineAI.entity.Reservation;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReservationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "diningTable", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "reservationStatus", ignore = true)
    @Mapping(target = "user", ignore = true)
    Reservation toReservation(CreateReservationRequest request);

    @Mapping(target = "customerName", ignore = true)
    @Mapping(target = "diningTableName", ignore = true)
    @Mapping(target = "restaurantName", ignore = true)
    @Mapping(target = "reservationStatusName", ignore = true)
    @Mapping(target = "userName", ignore = true)
    ReservationResponse toResponse(Reservation reservation);
}
