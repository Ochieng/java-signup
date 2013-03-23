package com.signup.service;

import com.signup.domain.Account;

public interface AccountService {

    void register(Account account);

    void delete(String username);

    boolean login(Account account);

    void resetPassword(String username);

    void update(Account account);

}
