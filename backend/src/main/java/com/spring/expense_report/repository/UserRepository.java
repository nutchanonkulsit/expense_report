package com.spring.expense_report.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.expense_report.dto.user.UserDTO;
import com.spring.expense_report.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // @Query(value = "SELECT u FROM User u", nativeQuery = false)
    @Query("SELECT u From User u")
    List<User> findAllUsers();

    @Query("SELECT new com.spring.expense_report.dto.user.UserDTO(u.id, u.name, u.email, u.createdAt, r.id, r.name) "
            + "FROM User u LEFT JOIN u.role r")
    List<UserDTO> findAllUsersDTO();

}