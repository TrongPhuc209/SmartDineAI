package com.SmartDineAI.entity;

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
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RSVT")
    private Long id;

    @Column(name = "START_TIME_RSVT")
    private LocalDateTime startTime;

    @Column(name = "END_TIME_RSVT")
    private LocalDateTime endTime;

    @Column(name = "GUEST_COUNT_RSVT")
    private Integer guestCount;

    @Column(name = "CREATED_AT_RSVT")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "ID_CUS")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "ID_DNT")
    private DiningTable diningTable;

    @ManyToOne
    @JoinColumn(name = "ID_RTR")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "ID_STATUS_RS")
    private ReservationStatus reservationStatus;

    @ManyToOne
    @JoinColumn(name = "ID_US")
    private User user;

    @PrePersist
    protected void onCreate(){
        createAt = LocalDateTime.now();
    }
}
