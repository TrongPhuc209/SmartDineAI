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

import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.dto.user.CreateUserRequest;
import com.SmartDineAI.dto.user.UpdateUserRequest;
import com.SmartDineAI.dto.user.UserResponse;
import com.SmartDineAI.entity.User;
import com.SmartDineAI.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody @Valid CreateUserRequest request){
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(){
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setResult(userService.getAllUsers());
        return response;
    }

    // @PostAuthorize("hasRole('ADMIN') or returnObject.result.username == authentication.name")
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

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ApiResponse<User> updateUser(@PathVariable @Valid Long userId, @RequestBody UpdateUserRequest request){
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.updateUser(userId, request));

        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable Long userId){
        ApiResponse<String> response = new ApiResponse<>();
        userService.deleteUser(userId);
        response.setMessage("User deleted successfully");
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/all/{passwordAdmin}")
    public ApiResponse<String> deleteAllUsers(@PathVariable String passwordAdmin){
        ApiResponse<String> response = new ApiResponse<>();
        userService.deleteAllUsers(passwordAdmin);
        response.setMessage("deleted successfully");
        return response;
    }

}
