package com.SmartDineAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SmartDineAI.entity.DiningTable;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long>{
    
}
