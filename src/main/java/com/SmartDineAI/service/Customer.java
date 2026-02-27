package com.SmartDineAI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.repository.CustomerRepository;

@Service
public class Customer {
    @Autowired
    CustomerRepository customerRepository;

    

}
