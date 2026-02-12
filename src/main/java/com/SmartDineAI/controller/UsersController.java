package com.SmartDineAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.dto.request.CreateUser;
import com.SmartDineAI.dto.request.UpdateUser;
import com.SmartDineAI.dto.response.ApiResponse;
import com.SmartDineAI.entity.Users;
import com.SmartDineAI.service.UsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping
    public ApiResponse<Users> createUser(@RequestBody @Valid CreateUser request){
        ApiResponse<Users> response = new ApiResponse<>();
        response.setResult(usersService.createUser(request));
        return response;
    }

    @GetMapping
    public ApiResponse<List<Users>> getAllUsers(){
        ApiResponse<List<Users>> response = new ApiResponse<>();
        response.setResult(usersService.getAllUsers());
        return response;
    }

    @GetMapping("/{userId}")
    public ApiResponse<Users> getUserById(@PathVariable Long userId){
        ApiResponse<Users> response = new ApiResponse<>();
        response.setResult(usersService.getUserById(userId));
        return response;
    }

    @PutMapping("/{userId}")
    public ApiResponse<Users> updateUser(@PathVariable @Valid Long userId, @RequestBody UpdateUser request){
        ApiResponse<Users> response = new ApiResponse<>();
        response.setResult(usersService.updateUser(userId, request));

        return response;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId){
        usersService.deleteUser(userId);
        return "User deleted successfully";
    }

    @DeleteMapping("/all/{passwordAdmin}")
    public String deleteAllUsers(@PathVariable String passwordAdmin){
        return usersService.deleteAllUsers(passwordAdmin);
    }
}
