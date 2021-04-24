package com.sivtcev.expensetracker.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
