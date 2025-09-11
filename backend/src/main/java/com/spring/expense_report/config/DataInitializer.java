package com.spring.expense_report.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.expense_report.entity.Role;
import com.spring.expense_report.repository.RoleRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository) {
        return args -> {
            // Initialize your database with default data here
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role("ADMIN"));
                roleRepository.save(new Role("USER"));
            }
        };
    }

}
