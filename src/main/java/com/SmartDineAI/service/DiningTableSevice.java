package com.SmartDineAI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.repository.DiningTableRepository;

@Service
public class DiningTableSevice {
    @Autowired
    DiningTableRepository diningTableRepository;
    
}
