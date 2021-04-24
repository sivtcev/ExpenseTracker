package com.sivtcev.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Category {

    private long categoryId;
    private long userId;
    private String title;
    private String description;
    private double totalExpense;
}
