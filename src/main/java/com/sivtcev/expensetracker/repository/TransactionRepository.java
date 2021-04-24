package com.sivtcev.expensetracker.repository;

import com.sivtcev.expensetracker.domain.Transaction;
import com.sivtcev.expensetracker.exception.EtBadRequestException;
import com.sivtcev.expensetracker.exception.EtResourceNotFoundException;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> findAll(long userId, long categoryId);

    Transaction findById(long userId, long categoryId, long transactionId) throws EtResourceNotFoundException;

    long create(long userId, long categoryId, double amount, String note, long transactionDate) throws EtBadRequestException;

    void update(long userId, long categoryId, long transactionId, Transaction transaction) throws EtBadRequestException;

    void removeById(long userId, long categoryId, long transactionId) throws EtResourceNotFoundException;
}
