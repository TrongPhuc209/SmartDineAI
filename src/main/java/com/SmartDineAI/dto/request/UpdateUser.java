package com.SmartDineAI.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUser {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Boolean isActive;
}
