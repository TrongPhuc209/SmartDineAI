package com.SmartDineAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SmartDineAI.entity.ReservationStatus;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Long>{
    
}
