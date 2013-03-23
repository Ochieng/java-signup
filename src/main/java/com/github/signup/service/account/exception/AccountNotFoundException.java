package com.github.signup.service.account.exception;

public class AccountNotFoundException extends AccountException {

    private final static String ACCOUNT_EXISTS_MESSAGE = "Account %s not found";

    private final String username;

    public AccountNotFoundException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return String.format(ACCOUNT_EXISTS_MESSAGE, username);
    }
}
