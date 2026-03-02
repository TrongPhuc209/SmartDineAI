package com.SmartDineAI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.SmartDineAI.dto.payment.CreatePaymentRequest;
import com.SmartDineAI.dto.payment.PaymentResponse;
import com.SmartDineAI.dto.payment.UpdatePaymentRequest;
import com.SmartDineAI.entity.Payment;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paidAt", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    Payment toPayment(CreatePaymentRequest request);

    @Mapping(target = "reservationId", source = "reservation.id")
    @Mapping(target = "paymentStatusName", source = "paymentStatus.statusName")
    PaymentResponse toResponse(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "methodName", ignore = true)
    @Mapping(target = "paidAt", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    void updatePayment(@MappingTarget Payment payment, UpdatePaymentRequest request);
}
