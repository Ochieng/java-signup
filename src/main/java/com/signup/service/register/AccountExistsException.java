package com.signup.service.register;

public class AccountExistsException extends RuntimeException {
    private final static String ACCOUNT_EXISTS_MESSAGE = "Operation forbidden for existing account: %s";

    private final String username;

    public AccountExistsException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return String.format(ACCOUNT_EXISTS_MESSAGE, username);
    }
}
