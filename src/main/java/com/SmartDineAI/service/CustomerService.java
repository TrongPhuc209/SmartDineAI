package com.SmartDineAI.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.customer.CreateCustomerRequest;
import com.SmartDineAI.dto.customer.CustomerResponse;
import com.SmartDineAI.dto.customer.UpdateCustomerRequest;
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

    public Page<CustomerResponse> getAllCustomer(Pageable pageable){
        return customerRepository.findAll(pageable)
                .map(customerMapper::toResponse);
    }
    
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request){
        Customer customer = customerRepository.findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        customerMapper.updateCustomer(customer, request);
        return customerMapper.toResponse(customer);
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }

    public Page<CustomerResponse> searchCustomer(Pageable pageable ,String phoneNumber){
        return customerRepository.searchCustomer(pageable ,phoneNumber).map(customerMapper::toResponse);
    }
}
