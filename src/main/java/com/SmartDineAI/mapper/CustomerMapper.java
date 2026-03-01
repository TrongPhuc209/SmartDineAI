package com.SmartDineAI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.SmartDineAI.dto.customer.CreateCustomerRequest;
import com.SmartDineAI.dto.customer.CustomerResponse;
import com.SmartDineAI.dto.customer.UpdateCustomerRequest;
import com.SmartDineAI.entity.Customer;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    Customer toCustomer(CreateCustomerRequest request);
    
    CustomerResponse toResponse(Customer customer);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    void updateCustomer(@MappingTarget Customer customer, UpdateCustomerRequest request);
}
