package com.spring.expense_report.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.expense_report.dto.RoleDTO;
import com.spring.expense_report.dto.UserDTO;
import com.spring.expense_report.entity.Role;
import com.spring.expense_report.entity.User;
import com.spring.expense_report.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // public List<User> getAllUsers() {
    //     return userRepository.findAll();
    // }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            // userDTO.setCreatedAt(new java.sql.Date(user.getCreatedAt().getTime()));
            userDTO.setCreatedAt(user.getCreatedAt());

            Role role = user.getRole();
            if (role != null) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(role.getId());
                roleDTO.setName(role.getName());
                userDTO.setRole(roleDTO);
            }

            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public List<UserDTO> getAllUsers2() {
        return userRepository.findAllUsersDTO();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Update user details
    public User updateUser(Long id, User user) {
        try {
            User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);

        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void deleteUser(Long id) {
        try {
            User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            userRepository.delete(existingUser);
        } catch (RuntimeException e) {
            throw e;
        }
    }

}
