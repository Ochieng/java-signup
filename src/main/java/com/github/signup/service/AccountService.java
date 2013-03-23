package com.github.signup.service;

import com.github.signup.domain.Account;

public interface AccountService {

    void register(Account account);

    void delete(String username);

    boolean authenticate(String username, String password);

    void resetPassword(String username);

    void update(Account account);

}
