package com.SmartDineAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SmartDineAI.entity.PaymentStatus;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
    
}
