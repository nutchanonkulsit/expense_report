package com.spring.expense_report.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.expense_report.dto.expense.ExpenseDTO;
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
            ExpenseDTO newExpense = expenseService.createExpense(expense);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        boolean deleted = expenseService.deleteExpense(id);

        if (deleted) {
            return ResponseEntity.ok("Expense deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Expense not found or could not be deleted");
        }
    }

    @GetMapping("/category")
    public ResponseEntity<?> getExpenseByCategory(@RequestParam Long userId, @RequestParam Long categoryId) {
        try {
            List<Expense> expenses = expenseService.getExpenseByCategory(userId, categoryId);
            return ResponseEntity.ok(expenses);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong: " + e.getMessage());
        }
    }

}
