package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.request.CreateUser;
import com.SmartDineAI.dto.request.UpdateUser;
import com.SmartDineAI.entity.Role;
import com.SmartDineAI.entity.User;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.repository.RoleRepository;
import com.SmartDineAI.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
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
