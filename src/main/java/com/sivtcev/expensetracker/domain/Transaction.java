package com.sivtcev.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transaction {

    private long transactionId;
    private long categoryId;
    private long userId;
    private double amount;
    private String note;
    private long transactionDate;

}
