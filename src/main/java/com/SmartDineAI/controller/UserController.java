package com.SmartDineAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.SmartDineAI.dto.response.UserResponse;
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

    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(){
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setResult(userService.getAllUsers());
        return response;
    }

    @PostAuthorize("hasRole('Admin') or returnObject.result.username == authentication.name")
    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long userId){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getUserById(userId));
        return response;
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo(){
        UserResponse userResponse = userService.getMyInfo();
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userResponse);
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
