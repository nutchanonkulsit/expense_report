package com.spring.expense_report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.expense_report.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
}
