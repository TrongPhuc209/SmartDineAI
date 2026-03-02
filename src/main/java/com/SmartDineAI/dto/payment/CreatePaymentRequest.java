package com.SmartDineAI.dto.payment;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentRequest {
    private BigDecimal amount;
    private String methodName;
    private String decription;
    private Long reservationId;
    private Long paymentStatusId;
}
