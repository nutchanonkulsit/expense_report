package com.spring.expense_report.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="description")
    private String description;
}
