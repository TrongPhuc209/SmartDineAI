package com.SmartDineAI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.customer.CreateCustomerRequest;
import com.SmartDineAI.dto.customer.CustomerResponse;
import com.SmartDineAI.entity.Customer;
import com.SmartDineAI.mapper.CustomerMapper;
import com.SmartDineAI.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;


    public CustomerResponse createCustomer(CreateCustomerRequest request){
        Customer customer = customerMapper.toCustomer(request);
        customerRepository.save(customer);
        CustomerResponse response = customerMapper.toResponse(customer);
        return response;
    }
    

}
