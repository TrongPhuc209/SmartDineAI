package com.SmartDineAI.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PM")
    private Long id;
    
    @Column(name = "AMOUNT_PM")
    private BigDecimal amount;

    @Column(name = "METHOD_PM")
    private String method;
    
    @Column(name = "STATUS_PM")
    private String decription;
    
    @Column(name = "PAID_AT_PM")
    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "ID_RSVT")
    private Reservation reservationId;

    @ManyToOne
    @JoinColumn(name = "ID_STATUS_PS")
    private PaymentStatus paymentStatusId;

    @PrePersist
    protected void onCreate(){
        paidAt = LocalDateTime.now();
    }
}
