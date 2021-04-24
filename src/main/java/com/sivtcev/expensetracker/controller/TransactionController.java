package com.sivtcev.expensetracker.controller;

import com.sivtcev.expensetracker.domain.Transaction;
import com.sivtcev.expensetracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest request,
                                                                @PathVariable("categoryId") long categoryId) {
        long userId = (long) request.getAttribute("userId");
        List<Transaction> transactions = transactionService.fetchAllTransactions(userId, categoryId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<Transaction> getTransactionsById(HttpServletRequest request,
                                                           @PathVariable("categoryId") long categoryId,
                                                           @PathVariable("transactionId") long transactionId) {
        long userId = (long) request.getAttribute("userId");
        Transaction transaction = transactionService.fetchTransactionById(userId, categoryId, transactionId);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request,
                                                      @PathVariable("categoryId") long categoryId,
                                                      @RequestBody Map<String, Object> transactionMap) {
        long userId = (long) request.getAttribute("userId");
        double amount = Double.valueOf(transactionMap.get("amount").toString());
        String note = (String) transactionMap.get("note");
        long transactionDate = (long) transactionMap.get("transactionDate");
        Transaction transaction = transactionService.addTransaction(userId, categoryId, amount, note, transactionDate);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") long categoryId,
                                                                  @PathVariable("transactionId") long transactionId,
                                                                  @RequestBody Transaction transaction) {
        long userId = (long) request.getAttribute("userId");
        transactionService.updateTransaction(userId, categoryId, transactionId, transaction);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") long categoryId,
                                                                  @PathVariable("transactionId") long transactionId) {
        long userId = (long) request.getAttribute("userId");
        transactionService.removeTransaction(userId, categoryId, transactionId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return ResponseEntity.ok(map);
    }
}
