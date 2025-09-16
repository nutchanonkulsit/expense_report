package com.spring.expense_report.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.expense_report.entity.Category;
import com.spring.expense_report.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @PostMapping("")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

}
