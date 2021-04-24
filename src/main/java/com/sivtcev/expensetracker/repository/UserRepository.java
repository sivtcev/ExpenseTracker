package com.sivtcev.expensetracker.repository;

import com.sivtcev.expensetracker.domain.User;
import com.sivtcev.expensetracker.exception.EtAuthException;

public interface UserRepository {

    Long create(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    long getCountByEmail(String email);

    User findById(long userId);
}
