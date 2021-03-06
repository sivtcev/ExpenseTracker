package com.sivtcev.expensetracker.repository;

import com.sivtcev.expensetracker.domain.Transaction;
import com.sivtcev.expensetracker.exception.EtBadRequestException;
import com.sivtcev.expensetracker.exception.EtResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private static final String SQL_FIND_ALL = "SELECT TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE " +
            "FROM ET_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ?";
    private static final String SQL_FIND_BY_ID = "SELECT TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE " +
            "FROM ET_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID =?";
    private static final String SQL_CREATE = "INSERT INTO ET_TRANSACTIONS (TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, " +
            "NOTE, TRANSACTION_DATE) VALUES(NEXTVAL('ET_TRANSACTIONS_SEQ'), ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ET_TRANSACTIONS SET AMOUNT = ?, NOTE = ?, TRANSACTION_DATE= ? " +
            "WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
    private static final String SQL_DELETE = "DELETE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(long userId, long categoryId) {
        return jdbcTemplate.query(SQL_FIND_ALL, transactionRowMapper, userId, categoryId);
    }

    @Override
    public Transaction findById(long userId, long categoryId, long transactionId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, transactionRowMapper, userId, categoryId, transactionId);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Transaction not found");
        }
    }

    @Override
    public long create(long userId, long categoryId, double amount, String note, long transactionDate) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setLong(1, categoryId);
                preparedStatement.setLong(2, userId);
                preparedStatement.setDouble(3, amount);
                preparedStatement.setString(4, note);
                preparedStatement.setLong(5, transactionDate);
                return preparedStatement;
            }, keyHolder);
            return (long) Objects.requireNonNull(keyHolder.getKeys()).get("TRANSACTION_ID");
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(long userId, long categoryId, long transactionId, Transaction transaction) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, transaction.getAmount(),
                    transaction.getNote(),
                    transaction.getTransactionDate(),
                    userId,
                    categoryId,
                    transactionId);
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(long userId, long categoryId, long transactionId) throws EtResourceNotFoundException {
        long count = jdbcTemplate.update(SQL_DELETE, userId, categoryId, transactionId);
        if(count == 0){
            throw new EtResourceNotFoundException("Transaction not found");
        }
    }

    private final RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> new Transaction(rs.getLong("TRANSACTION_ID"),
            rs.getLong("CATEGORY_ID"),
            rs.getLong("USER_ID"),
            rs.getDouble("AMOUNT"),
            rs.getString("NOTE"),
            rs.getLong("TRANSACTION_DATE")));
}
