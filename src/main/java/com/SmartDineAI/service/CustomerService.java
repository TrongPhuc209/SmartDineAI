package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.customer.CreateCustomerRequest;
import com.SmartDineAI.dto.customer.CustomerResponse;
import com.SmartDineAI.entity.Customer;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
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

    public CustomerResponse getCustomerById(Long id){
        Customer customer = customerRepository.findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        return customerMapper.toResponse(customer);
    }

    public List<CustomerResponse> getAllCustomer(){
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }
    
    public CustomerResponse updateCustomer(Long id, CreateCustomerRequest request){
        Customer customer = customerRepository.findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        customer.setFullName(request.getFullName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setEmail(request.getEmail());
        customer.setNote(request.getNote());
        
        customerRepository.save(customer);
        return customerMapper.toResponse(customer);
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
}
