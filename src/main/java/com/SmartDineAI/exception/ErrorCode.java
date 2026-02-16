package com.SmartDineAI.exception;

public enum ErrorCode {
    INVALID_REQUEST(400, "Invalid request data"),
    VALIDATION_FAILED(400, "Validation failed"),
    ROLE_NOT_EXIST(400, "Role does not exist"),
    INVALID_ROLE(400, "Invalid role"),
    INVALID_FORMAT_USERNAME(400, "Invalid username format. Must be 3-50 characters"),
    INVALID_FORMAT_PASSWORD(400, "Invalid password format. Must be 6-100 characters"),
    INVALID_FORMAT_EMAIL(400, "Invalid email format"),
    INVALID_FORMAT_PHONE_NUMBER(400, "Invalid phone number format. Must be numeric and up to 15 digits"),
    MISSING_REQUIRED_FIELD(400, "Missing required field"),

    UNAUTHENTICATED(401, "Authentication required"),
    INVALID_CREDENTIALS(401, "Invalid username or password"),
    TOKEN_EXPIRED(401, "Token has expired"),
    INVALID_TOKEN(401, "Invalid token"),

    ACCESS_DENIED(403, "You do not have permission"),
    FORBIDDEN_ACTION(403, "Action is not allowed"),

    USER_NOT_FOUND(404, "User not found"),
    ROLE_NOT_FOUND(404, "Role not found"),
    RESOURCE_NOT_FOUND(404, "Resource not found"),

    USER_ALREADY_EXISTS(409, "Username already exists"),
    EMAIL_ALREADY_EXISTS(409, "Email already exists"),
    DATA_CONFLICT(409, "Data conflict"),

    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    DATABASE_ERROR(500, "Database error"),
    UNKNOWN_ERROR(500, "An unknown error occurred")

    ;
    private final int status;
    private final String message;
    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }

    
}
