package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.request.CreateUser;
import com.SmartDineAI.dto.request.UpdateUser;
import com.SmartDineAI.dto.response.UserResponse;
import com.SmartDineAI.entity.Role;
import com.SmartDineAI.entity.User;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.repository.RoleRepository;
import com.SmartDineAI.repository.UserRepository;

import lombok.extern.slf4j.*;;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private UserResponse mapToUserResponse(User user){
        UserResponse responseDto = new UserResponse();
        responseDto.setId(user.getId());
        responseDto.setUsername(user.getUsername());
        responseDto.setFullName(user.getFullName());
        responseDto.setEmail(user.getEmail());
        responseDto.setPhoneNumber(user.getPhoneNumber());
        responseDto.setCreatedAt(user.getCreatedAt());
        responseDto.setIsActive(user.getIsActive());
        responseDto.setRole(user.getRoleId());
        return responseDto;
    }

    public User createUser(CreateUser request){
        User user = new User();

        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        Role role = roleRepository.findById(2L).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setUsername(request.getUsername());
        // Hash the password before saving
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFullName(request.getFullName());
        user.setRoleId(role);

        return userRepository.save(user);
    }
    
    public UserResponse getUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        UserResponse response = mapToUserResponse(user);
        return response;
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(context).orElseThrow(() ->new AppException(ErrorCode.USER_NOT_FOUND));
        UserResponse userResponse = mapToUserResponse(user);
        return userResponse;
    }

    public List<UserResponse> getAllUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("username: {}", authentication.getName());
        authentication.getAuthorities().forEach(info -> log.info(info.getAuthority()));

        return userRepository.findAll()
                                .stream()
                                .map(this::mapToUserResponse)
                                .toList();
    }

    public User updateUser(Long userId, UpdateUser request){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setIsActive(request.getIsActive());
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public String deleteAllUsers(String passwordAdmin){
        if(passwordAdmin.equals("admin333")){
            userRepository.deleteAll();
            return "All users deleted successfully";
        }
        throw new RuntimeException("Invalid admin password");
    }
}
