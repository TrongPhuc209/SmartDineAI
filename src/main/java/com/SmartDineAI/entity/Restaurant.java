package com.SmartDineAI.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RTR")
    private Long id;

    @Column(name = "NAME_RTR")
    private String name;

    @Column(name = "ADDRESS_RTR")
    private String address;

    @Column(name = "PHONE_RTR")
    private String phoneNumber;

    @Column(name = "DECRIPTION_RTR")
    private String decription;

    @Column(name = "OPEN_TIME_RTR")
    private LocalTime openTime;

    @Column(name = "CLOSE_TIME_RTR")
    private LocalTime closeTime;

    @Column(name = "IS_ACTIVE_RTR")
    private Boolean isActive;

    @Column(name = "CREATED_AT_RTR")
    private LocalDateTime createAt;

    @PrePersist
    protected void onCreate(){
        isActive = true;
        createAt = LocalDateTime.now();
    }
}
