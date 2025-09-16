package com.spring.expense_report.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.expense_report.entity.Expense;
import com.spring.expense_report.repository.ExpenseRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // public Expense createExpense(Expense expense) {
    //     return expenseRepository.save(expense);
    // }

    public Expense createExpense(Expense expenseRequest) {

        Expense expense = new Expense();
        expense.setTitle(expenseRequest.getTitle());
        expense.setDescription(expenseRequest.getDescription());
        expense.setAmount(expenseRequest.getAmount());
        expense.setDate(expenseRequest.getDate() != null ? expenseRequest.getDate() : LocalDate.now());
        expense.setUserId(expenseRequest.getUserId());
        expense.setCategoryId(expenseRequest.getCategoryId());

        // Expense savedExpense = expenseRepository.save(expense);
        // return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);

        return expenseRepository.save(expense);
    }

}
