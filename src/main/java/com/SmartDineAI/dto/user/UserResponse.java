package com.SmartDineAI.dto.user;

import java.time.LocalDateTime;

import com.SmartDineAI.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private Boolean isActive;
    private Role role;
}
