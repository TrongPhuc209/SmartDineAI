package com.SmartDineAI.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody @Valid CreateUserRequest request){
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<List<UserResponse>> getAllUsers(){
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setResult(userService.getAllUsers());
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long userId){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getUserById(userId));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo(){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getMyInfo());
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable @Valid Long userId, @RequestBody @Valid UpdateUserRequest request){
        ApiResponse<UserResponse> response = new ApiResponse<>();
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

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/all/{passwordAdmin}")
    public ApiResponse<String> deleteAllUsers(@PathVariable String passwordAdmin){
        userService.deleteAllUsers(passwordAdmin);
        return ApiResponse.<String>builder()
                            .message("deleted successfully")
                            .build();
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<UserResponse>> searchUser(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate){
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setResult(userService.searchUser(keyword, roleId, isActive, fromDate, toDate));
        return response;
    }
}
