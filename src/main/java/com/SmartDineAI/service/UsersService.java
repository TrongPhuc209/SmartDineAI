package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.dto.request.CreateUser;
import com.SmartDineAI.dto.request.UpdateUser;
import com.SmartDineAI.entity.Role;
import com.SmartDineAI.entity.Users;
import com.SmartDineAI.repository.RoleRepository;
import com.SmartDineAI.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Users createUser(CreateUser request){
        Users user = new Users();

        Role role = roleRepository.findById(2L).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setRoleId(role);

        return usersRepository.save(user);
    }

    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    public Users getUserById(Long userId){
        return usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Users updateUser(Long userId, UpdateUser request){
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setIsActive(request.getIsActive());
        
        return usersRepository.save(user);
    }
    
    public void deleteUser(Long userId){
        usersRepository.deleteById(userId);
    }
}
