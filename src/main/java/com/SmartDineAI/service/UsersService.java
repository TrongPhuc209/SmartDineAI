package com.SmartDineAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Users createUser(Users request){
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
}
