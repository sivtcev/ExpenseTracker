package com.sivtcev.expensetracker.service;

import com.sivtcev.expensetracker.domain.User;
import com.sivtcev.expensetracker.exception.EtAuthException;

public interface UserService {

    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String firsName, String lastName, String email, String password) throws EtAuthException;
}
