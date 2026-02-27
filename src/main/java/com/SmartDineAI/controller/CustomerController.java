package com.SmartDineAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.dto.customer.CreateCustomerRequest;
import com.SmartDineAI.dto.customer.CustomerResponse;
import com.SmartDineAI.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@RequestBody @Valid CreateCustomerRequest request){
        ApiResponse<CustomerResponse> response = new ApiResponse<>();
        response.setResult(customerService.createCustomer(request));
        return response;
    }
}
