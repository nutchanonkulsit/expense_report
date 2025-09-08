package com.spring.expense_report.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RoleDTO {
    private Long id;
    private String name;

    public RoleDTO() {
        super();
    }

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
