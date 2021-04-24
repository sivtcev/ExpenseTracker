package com.sivtcev.expensetracker.repository;

import com.sivtcev.expensetracker.domain.User;
import com.sivtcev.expensetracker.exception.EtAuthException;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) " +
            "VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM ET_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM ET_USERS WHERE EMAIL = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long create(String firstName, String lastName, String email, String password) throws EtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, hashedPassword);
                return preparedStatement;
            }, keyHolder);
            return (long) Objects.requireNonNull(keyHolder.getKeys()).get("USER_ID");
        } catch (Exception e){
            throw new EtAuthException("Invalid details. Failed to create account.");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, userRowMapper, email);
            if(!BCrypt.checkpw(password, Objects.requireNonNull(user).getPassword())){
                throw new EtAuthException("Invalid email or password");
            }
        } catch (EmptyResultDataAccessException e){
            throw new EtAuthException("Invalid email or password");
        }
        return null;
    }

    @Override
    public long getCountByEmail(String email) {
        Long count = jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, Long.class, email);
        return count == null ? 0 : count;
    }

    @Override
    public User findById(long userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, userRowMapper, userId);
    }

    private final RowMapper<User> userRowMapper = ((rs, rowNum) -> new User(rs.getLong("USER_ID"),
            rs.getString("FIRST_NAME"),
            rs.getString("LAST_NAME"),
            rs.getString("EMAIL"),
            rs.getString("PASSWORD")));
}
