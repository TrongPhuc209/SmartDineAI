package com.SmartDineAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.entity.Users;
import com.SmartDineAI.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping
    public Users createUser(@RequestBody Users request){
        return usersService.createUser(request);
    }

    @GetMapping
    public List<Users> getAllUsers(){
        return usersService.getAllUsers();
    }
}
