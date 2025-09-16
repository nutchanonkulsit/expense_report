package com.spring.expense_report.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.expense_report.entity.Expense;
import com.spring.expense_report.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping("")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping("")
    public ResponseEntity<?> createExpense(@RequestBody Expense expense) {
        try {
            Expense newExpense = expenseService.createExpense(expense);
            return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
        } catch (Exception e) {
            // Log the error (optional but recommended)
            e.printStackTrace();

            // Return 500 Internal Server Error with a message
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create expense: " + e.getMessage());
        }
    }

}
