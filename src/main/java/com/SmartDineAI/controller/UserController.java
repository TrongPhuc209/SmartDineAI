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
import com.SmartDineAI.entity.User;
import com.SmartDineAI.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody @Valid CreateUser request){
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    @GetMapping
    public ApiResponse<List<User>> getAllUsers(){
        ApiResponse<List<User>> response = new ApiResponse<>();
        response.setResult(userService.getAllUsers());
        return response;
    }

    @GetMapping("/{userId}")
    public ApiResponse<User> getUserById(@PathVariable Long userId){
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.getUserById(userId));
        return response;
    }

    @PutMapping("/{userId}")
    public ApiResponse<User> updateUser(@PathVariable @Valid Long userId, @RequestBody UpdateUser request){
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.updateUser(userId, request));

        return response;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return "User deleted successfully";
    }

    @DeleteMapping("/all/{passwordAdmin}")
    public String deleteAllUsers(@PathVariable String passwordAdmin){
        return userService.deleteAllUsers(passwordAdmin);
    }
}
