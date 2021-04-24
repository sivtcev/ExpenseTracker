package com.sivtcev.expensetracker.service;

import com.sivtcev.expensetracker.domain.Transaction;
import com.sivtcev.expensetracker.exception.EtBadRequestException;
import com.sivtcev.expensetracker.exception.EtResourceNotFoundException;

import java.util.List;

public interface TransactionService {

    List<Transaction> fetchAllTransactions(long userId, long categoryId) throws EtResourceNotFoundException;

    Transaction fetchTransactionById(long userId, long categoryId, long transactionId) throws EtResourceNotFoundException;

    Transaction addTransaction(long userId, long categoryId, double amount, String note, long transactionDate) throws EtBadRequestException;

    void updateTransaction(long userId, long categoryId, long transactionId, Transaction transaction) throws EtBadRequestException;

    void removeTransaction(long userId, long categoryId, long transactionId) throws EtResourceNotFoundException;

}
