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
    @Size(min = 3, max = 50, message = "INVALID_FORMAT_USERNAME")
    private String username;

    @Size(min = 6, max = 100, message = "INVALID_FORMAT_PASSWORD")
    private String password;

    @NotNull(message = "MISSING_REQUIRED_FIELD")
    private String fullName;

    @Email(message = "INVALID_FORMAT_EMAIL")
    private String email;

    @Digits(integer = 15, fraction = 0, message = "INVALID_FORMAT_PHONE_NUMBER")
    private String phoneNumber;

    private Boolean isActive;
}
