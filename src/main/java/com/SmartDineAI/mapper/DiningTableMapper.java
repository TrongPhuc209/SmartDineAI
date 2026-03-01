package com.SmartDineAI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.SmartDineAI.dto.diningTable.CreateDiningTableRequest;
import com.SmartDineAI.dto.diningTable.DiningTableResponse;
import com.SmartDineAI.dto.diningTable.UpdateDiningTableRequest;
import com.SmartDineAI.entity.DiningTable;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DiningTableMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    DiningTable toDiningTable(CreateDiningTableRequest request);
    
    @Mapping(target = "restaurantId", ignore = true)
    @Mapping(target = "active", ignore = true)
    DiningTableResponse toResponse(DiningTable diningTable);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    void updateDiningTable(@MappingTarget DiningTable diningTable, UpdateDiningTableRequest request);
}
