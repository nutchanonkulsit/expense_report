package com.spring.expense_report.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.expense_report.dto.expense.ExpenseDTO;
import com.spring.expense_report.dto.user.UserDTO;
import com.spring.expense_report.entity.Category;
import com.spring.expense_report.entity.Expense;
import com.spring.expense_report.entity.User;
import com.spring.expense_report.repository.CategoryRepository;
import com.spring.expense_report.repository.ExpenseRepository;
import com.spring.expense_report.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // public Expense createExpense(Expense expense) {
    // return expenseRepository.save(expense);
    // }

    public ExpenseDTO createExpense(Expense expenseRequest) {

        Expense expense = new Expense();
        expense.setTitle(expenseRequest.getTitle());
        expense.setDescription(expenseRequest.getDescription());
        expense.setAmount(expenseRequest.getAmount());
        expense.setDate(expenseRequest.getDate() != null ? expenseRequest.getDate() : LocalDate.now());

        User user = userRepository.findById(expenseRequest.getUserId().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        expense.setUserId(user);

        Category category = categoryRepository.findById(expenseRequest.getCategoryId().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        expense.setCategoryId(category);

        expenseRepository.save(expense);


        // Response Body
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setId(expense.getId());
        expenseDTO.setTitle(expense.getTitle());
        expenseDTO.setDescription(expense.getDescription());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setDate(expense.getDate());

        UserDTO userDTO = new UserDTO(user.getFirstName(), user.getLastName());
        expenseDTO.setUser(userDTO);

        // expenseDTO.setFirstName(user.getFirstName());
        // expenseDTO.setLastName(user.getLastName());

        expenseDTO.setCategoryName(category.getName());

        // Expense savedExpense = expenseRepository.save(expense);
        // return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);

        return expenseDTO;
    }

    public boolean deleteExpense(Long id) {
        try {
            if (!expenseRepository.existsById(id)) {
                return false; // Not found
            }
            expenseRepository.deleteById(id);
            return true; // Deleted successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Something went wrong
        }
    }

    public List<Expense> getExpenseByCategory(Long userId, Long categoryId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }

        return expenseRepository.findExpenseByCategoryId(userId, categoryId);
    }

}
