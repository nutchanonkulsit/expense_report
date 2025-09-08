package com.spring.expense_report.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.expense_report.entity.Role;
import com.spring.expense_report.repository.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

}
