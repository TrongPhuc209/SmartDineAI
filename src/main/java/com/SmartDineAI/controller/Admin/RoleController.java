package com.SmartDineAI.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.SmartDineAI.dto.auth.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.entity.Role;
import com.SmartDineAI.service.RoleService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Role createRole(@RequestBody Role requestRole){
        return roleService.createRole(requestRole);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<Page<Role>> getAllRoles(Pageable pageable){
        Page<Role> result = roleService.getAllRoles(pageable);
        return ApiResponse.<Page<Role>>builder()
                .result(result)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }
}
