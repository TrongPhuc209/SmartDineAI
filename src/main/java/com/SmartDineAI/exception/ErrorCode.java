package com.SmartDineAI.exception;

public enum ErrorCode {

    INVALID_KEY(999, "Invalid key in request"),


    MISSING_REQUIRED_FIELD(400, "Missing required field"),
    INVALID_REQUEST(400, "Invalid request data"),
    // USER
    VALIDATION_FAILED(400, "Validation failed"),
    INVALID_FORMAT_USERNAME(400, "Invalid username format. Must be 3-50 characters"),
    INVALID_FORMAT_PASSWORD(400, "Invalid password format. Must be 6-100 characters"),
    INVALID_FORMAT_EMAIL(400, "Invalid email format"),
    INVALID_FORMAT_PHONE_NUMBER(400, "Invalid phone number format. Must be numeric and up to 15 digits"),
    // ROLE
    INVALID_ROLE(400, "Invalid role"),
    ROLE_NOT_EXIST(400, "Role does not exist"),
    // RESTAURANT
    INVALID_FORMAT_RESTAURANT_NAME(400, "Invalid restaurant name format. Must be 1-50 characters"),
    INVALID_FORMAT_RESTAURANT_ADDRESS(400, "Invalid restaurant name format. Must be 10-100 characters"),
    INVALID_FORMAT_RESTAURANT_PHONE_NUMBER(400, "Invalid phone number format. Must be numeric and up to 15 digits"),
    
    INVALID_TIME_RANGE(400, "Invald time range"),


    UNAUTHENTICATED(401, "Authentication required"),
    INVALID_CREDENTIALS(401, "Invalid username or password"),
    TOKEN_EXPIRED(401, "Token has expired"),
    INVALID_TOKEN(401, "Invalid token"),
    TOKEN_GENERATION_FAILED(401, "Failed to generate authentication token"),


    ACCESS_DENIED(403, "You do not have permission"),
    FORBIDDEN_ACTION(403, "Action is not allowed"),


    ID_NOT_FOUND(404, "ID not found"),
    USER_NOT_FOUND(404, "User not found"),
    ROLE_NOT_FOUND(404, "Role not found"),
    RESOURCE_NOT_FOUND(404, "Resource not found"),
    RESTAURANT_NOT_FOUND(404, "Restaurant not found"),
    RESTAURANT_NAME_NOT_FOUND(404, "Restaurant name not found"),
    DINING_TABLE_NOT_FOUND(404, "DiningTable not found"),
    CUSTOMER_NOT_FOUND(404, "Customer not found"),
    RESERVATION_NOT_FOUND(404, "Reservation not found"),
    RESERVATION_STATUS_NOT_FOUND(404, "Reservation status not found"),
    

    USER_ALREADY_EXISTS(409, "Username already exists"),
    EMAIL_ALREADY_EXISTS(409, "Email already exists"),
    DATA_CONFLICT(409, "Data conflict"),
    RESTAURANT_NAME_ALREADY_EXISTS(409, "Restaurant name already exists"),
    TABLE_ALREADY_BOOKED(409, "Table already booked"),


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
