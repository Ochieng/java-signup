package com.signup.domain.dao;

import com.signup.domain.Account;

public interface AccountDAO {

    boolean exists(String username);

    boolean exists(String username, String password);

    void add(Account account);

    void remove(String username);

    void update(Account account);

    Account load(String username);
}
