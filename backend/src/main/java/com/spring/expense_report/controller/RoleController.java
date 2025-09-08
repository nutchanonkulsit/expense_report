package com.spring.expense_report.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.expense_report.entity.Role;
import com.spring.expense_report.service.RoleService;

import lombok.RequiredArgsConstructor;





@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;
    
    @GetMapping()
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
    
    @PostMapping()
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }
    

}
