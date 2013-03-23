package com.signup.domain.dao;

import com.signup.domain.Account;

public interface AccountDAO {

    boolean exists(String username);

    void add(Account account);

    void remove(String username);
}
