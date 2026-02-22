package com.SmartDineAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.dto.request.AuthenticationRequest;
import com.SmartDineAI.dto.request.IntrospectRequest;
import com.SmartDineAI.dto.response.ApiResponse;
import com.SmartDineAI.dto.response.AuthenticationResponse;
import com.SmartDineAI.dto.response.IntrospectResponse;
import com.SmartDineAI.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        var authResponse = authService.login(request);
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>();
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

    // @PostMapping("/introspect")
    // public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request){
    //     ApiResponse<IntrospectResponse> response = new ApiResponse<>();
    //     var introspectResponse = authService.introspect(request);
    //     response.setResult(introspectResponse);
    //     return response;
    // }
}
