package com.SmartDineAI.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.user.CreateUserRequest;
import com.SmartDineAI.dto.user.UpdateUserRequest;
import com.SmartDineAI.dto.user.UserResponse;
import com.SmartDineAI.entity.Role;
import com.SmartDineAI.entity.User;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.mapper.UserMapper;
import com.SmartDineAI.repository.RoleRepository;
import com.SmartDineAI.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(CreateUserRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        Role role = roleRepository.findById(2L).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        return userRepository.save(user);
    }
    
    public UserResponse getUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(context).orElseThrow(() ->new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                                .stream()
                                .map(userMapper::toResponse)
                                .toList();
    }

    public UserResponse updateUser(Long userId, UpdateUserRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);
        if(request.getPassword() != null && !request.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userMapper.toResponse(user);
    }
    
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    public String deleteAllUsers(String passwordAdmin){
        if(passwordAdmin.equals("admin333")){
            userRepository.deleteAll();
            return "All users deleted successfully";
        }
        throw new RuntimeException("Invalid admin password");
    }

    public List<UserResponse> searchUser(String keyword, Long roleId, Boolean isActive, LocalDateTime fromDate, LocalDateTime toDate){
        Role role = null;
        if(roleId != null){
            role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        }

        return userRepository.searchUser(keyword, role, isActive, fromDate, toDate)
                            .stream()
                            .map(userMapper::toResponse)
                            .toList();
    }
}
