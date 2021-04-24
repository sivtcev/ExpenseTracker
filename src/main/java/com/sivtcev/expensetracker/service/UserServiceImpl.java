package com.sivtcev.expensetracker.service;

import com.sivtcev.expensetracker.domain.User;
import com.sivtcev.expensetracker.exception.EtAuthException;
import com.sivtcev.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if(email != null){
            email = email.toLowerCase();
        }
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firsName, String lastName, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        if (email != null) {
            email = email.toLowerCase();
        }

        if (!pattern.matcher(Objects.requireNonNull(email)).matches()) {
            throw new EtAuthException("Invalid email format");
        }

        long count = userRepository.getCountByEmail(email);

        if (count > 0) {
            throw new EtAuthException("Email already in use");
        }

        long userId = userRepository.create(firsName, lastName, email, password);

        return userRepository.findById(userId);
    }
}
