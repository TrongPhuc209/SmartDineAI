package com.SmartDineAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.dto.auth.AuthenticationRequest;
import com.SmartDineAI.dto.auth.AuthenticationResponse;
import com.SmartDineAI.dto.auth.IntrospectRequest;
import com.SmartDineAI.dto.auth.IntrospectResponse;
import com.SmartDineAI.dto.auth.RegisterRequest;
import com.SmartDineAI.service.AuthService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        var authResponse = authService.login(request);
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>();
        response.setResult(authResponse);
        return response;
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody @Valid RegisterRequest request){
        var authResponse = authService.register(request);
        ApiResponse<String> response = new ApiResponse<>();
        response.setResult(authResponse);
        return response;
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request){
        IntrospectResponse introspect = authService.introspect(request);
        ApiResponse<IntrospectResponse> response = new ApiResponse<>();
        response.setResult(introspect);
        return response;
    }

    // Thiếu đổi mật khẩu
}
