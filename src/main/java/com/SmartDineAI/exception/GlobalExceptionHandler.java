package com.SmartDineAI.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.SmartDineAI.dto.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppExceptions(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse response = new ApiResponse();

        response.setCode(errorCode.getStatus());
        response.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeExceptions(RuntimeException exception) {
        ApiResponse response = new ApiResponse();
        response.setCode(400);
        response.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        String keyStatus = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(keyStatus);
        ApiResponse response = new ApiResponse();

        response.setCode(errorCode.getStatus());
        response.setMessage(errorCode.getMessage());
        
        return ResponseEntity.status(errorCode.getStatus()).body(response);    
    }
}
