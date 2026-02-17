package com.SmartDineAI.entity;

import java.time.LocalDate;

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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_US")
    private Long id;

    @Column(name = "USERNAME_US")
    private String username;

    @Column(name = "PASSWORD_US")
    private String password;

    @Column(name = "FULL_NAME_US")
    private String fullName;

    @Column(name = "EMAIL_US")
    private String email;

    @Column(name = "PHONE_US")
    private String phoneNumber;

    @Column(name = "CREATED_AT_US")
    private LocalDate createdAt;

    @Column(name = "IS_ACTIVE_US")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "ID_R")
    private Role roleId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        isActive = true;
    }

}
