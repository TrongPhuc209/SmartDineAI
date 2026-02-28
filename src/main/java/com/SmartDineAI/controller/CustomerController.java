package com.SmartDineAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.dto.customer.CreateCustomerRequest;
import com.SmartDineAI.dto.customer.CustomerResponse;
import com.SmartDineAI.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@RequestBody @Valid CreateCustomerRequest request){
        ApiResponse<CustomerResponse> response = new ApiResponse<>();
        response.setResult(customerService.createCustomer(request));
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable Long id){
        ApiResponse<CustomerResponse> response = new ApiResponse<>();
        response.setResult(customerService.getCustomerById(id));
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<CustomerResponse>> getAllCustomer(){
        ApiResponse<List<CustomerResponse>> response = new ApiResponse<>();
        response.setResult(customerService.getAllCustomer());
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody @Valid CreateCustomerRequest request){
        ApiResponse<CustomerResponse> response = new ApiResponse<>();
        response.setResult(customerService.updateCustomer(id, request));
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCustomer(@PathVariable Long id){
        ApiResponse<String> response = new ApiResponse<>();
        customerService.deleteCustomer(id);
        response.setMessage("Customer deleted successfully");
        return response;
    }
}
