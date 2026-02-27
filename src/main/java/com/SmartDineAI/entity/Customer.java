package com.SmartDineAI.entity;

import java.time.LocalDateTime;

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
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CUS")
    private Long id;

    @Column(name = "FULL_NAME_CUS")
    private String fullName;

    @Column(name = "PHONE_CUS")
    private String phoneNumber;

    @Column(name = "EMAIL_CUS")
    private String email;

    @Column(name = "NOTE_CUS")
    private String note;

    @Column(name = "CREATED_AT_CUS")
    private LocalDateTime createAt;

    @PrePersist
    protected void onCreate(){
        createAt = LocalDateTime.now();
    }
}
