package com.SmartDineAI.entity;

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
@Table(name = "dining_table")
public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DNT")
    private Long id;

    @Column(name = "TABLE_CODE_DNT")
    private String tableCode;

    @Column(name = "CAPACITY_DNT")
    private int capacity;

    @Column(name = "IS_ACTIVE_DNT")
    private boolean isActive;

    @Column(name = "LOCATIN_DNT")
    private String location;

    @ManyToOne
    @JoinColumn(name = "ID_RTR")
    private Restaurant restaurantId;

    @PrePersist
    protected void onCreate(){
        isActive = true;
        capacity = 0;
    }
}
