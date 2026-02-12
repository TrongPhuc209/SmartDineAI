package com.SmartDineAI.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUser {
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @NotNull(message = "Full name cannot be null")
    private String fullName;

    @Email(message = "Email should be valid")
    private String email;

    @Digits(integer = 15, fraction = 0, message = "Phone number must be numeric and up to 15 digits")
    private String phoneNumber;
    
    private Boolean isActive;
}
