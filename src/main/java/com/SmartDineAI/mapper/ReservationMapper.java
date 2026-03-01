package com.SmartDineAI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.SmartDineAI.dto.reservation.CreateReservationRequest;
import com.SmartDineAI.dto.reservation.ReservationResponse;
import com.SmartDineAI.dto.reservation.UpdateReservationRequest;
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

    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "diningTableName", source = "diningTable.tableCode")
    @Mapping(target = "restaurantName", source = "restaurant.name")
    @Mapping(target = "reservationStatusName", source = "reservationStatus.statusName")
    @Mapping(target = "userName", source = "user.username")
    ReservationResponse toResponse(Reservation reservation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "diningTable", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "reservationStatus", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateReservation(@MappingTarget Reservation reservation, UpdateReservationRequest request);
}
