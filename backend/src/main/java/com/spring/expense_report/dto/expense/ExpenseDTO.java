package com.spring.expense_report.dto.expense;

import java.time.LocalDate;

import com.spring.expense_report.dto.user.UserDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ExpenseDTO {
    private Long id;
    private String title;
    private String description;
    private Double amount;
    private LocalDate date;

    private UserDTO user;
    // private Long userId;
    // private String firstName;
    // private String lastName;

    // private Long categoryId;
    private String categoryName;

    public ExpenseDTO () {
        super();
    }

    public ExpenseDTO(Long id, String title,
            String description, Double amount,
            LocalDate date, String firstName, String lastName,
            String categoryName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.user = new UserDTO(firstName, lastName);
        this.categoryName = categoryName;
    }

}
