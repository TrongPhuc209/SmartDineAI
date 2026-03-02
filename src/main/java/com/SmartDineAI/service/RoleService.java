package com.SmartDineAI.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SmartDineAI.entity.Role;
import com.SmartDineAI.exception.AppException;
import com.SmartDineAI.exception.ErrorCode;
import com.SmartDineAI.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role requestRole){
        Role role = new Role();

        role.setName(requestRole.getName());
        role.setDescription(requestRole.getDescription());

        return roleRepository.save(role);
    }

    public Page<Role> getAllRoles(Pageable pageable){
        return roleRepository.findAll(pageable);
    }

    public Role getRoleById(Long id){
        return roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }
}
