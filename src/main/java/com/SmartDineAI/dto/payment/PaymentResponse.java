package com.SmartDineAI.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private BigDecimal amount;
    private String methodName;
    private String decription;
    private LocalDateTime paidAt;
    private String reservationName;
    private String paymentStatusName;
}
