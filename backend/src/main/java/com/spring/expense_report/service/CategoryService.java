package com.spring.expense_report.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.expense_report.entity.Category;
import com.spring.expense_report.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

}
