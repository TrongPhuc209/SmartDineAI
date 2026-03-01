package com.SmartDineAI.dto.user;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    @Size(min = 3, max = 50, message = "INVALID_FORMAT_USERNAME")
    private String username;

    @Size(min = 6, max = 100, message = "INVALID_FORMAT_PASSWORD")
    private String password;

    private String fullName;

    @Email(message = "INVALID_FORMAT_EMAIL")
    private String email;

    @Digits(integer = 15, fraction = 0, message = "INVALID_FORMAT_PHONE_NUMBER")
    private String phoneNumber;

    private Boolean isActive;
}
