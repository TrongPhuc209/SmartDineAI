package com.SmartDineAI.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.SmartDineAI.dto.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppExceptions(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<Void> response = new ApiResponse<>();

        response.setCode(errorCode.getStatus());
        response.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeExceptions(RuntimeException exception) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(400);
        response.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        String keyStatus = exception.getFieldError().getDefaultMessage();
        ApiResponse<Void> response = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        
        try {
            errorCode = ErrorCode.valueOf(keyStatus);
        } catch (IllegalArgumentException e) {
        }


        response.setCode(errorCode.getStatus());
        response.setMessage(errorCode.getMessage());
        
        return ResponseEntity.status(errorCode.getStatus()).body(response);    
    }
}
