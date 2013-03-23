package com.signup.domain.dao;

import com.signup.domain.Account;

public interface AccountDAO {

    boolean exists(Account account);

    void add(Account account);

}
