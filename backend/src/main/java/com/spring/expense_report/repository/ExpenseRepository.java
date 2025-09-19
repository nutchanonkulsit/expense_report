package com.spring.expense_report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.expense_report.dto.expense.ExpenseDTO;
import com.spring.expense_report.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e From Expense e")
    List<ExpenseDTO> findAllExpense();

    @Query("SELECT e FROM Expense e " +
            "INNER JOIN Category c ON c.id = e.categoryId.id " +
            "JOIN User u ON u.id = e.userId.id " +
            "WHERE c.id = :categoryId AND u.id = :userId")
    List<Expense> findExpenseByCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

}
