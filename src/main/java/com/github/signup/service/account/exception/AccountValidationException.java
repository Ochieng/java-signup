package com.github.signup.service.account.exception;

public class AccountValidationException extends RuntimeException {

    public AccountValidationException() {
    }

    public AccountValidationException(String message) {
        super(message);
    }
}
