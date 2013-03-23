package com.github.signup.service.account.validator;

import com.github.signup.domain.Account;
import com.github.signup.service.account.AccountValidator;
import com.github.signup.service.account.exception.AccountValidationException;

public class NotNullAccountValidator implements AccountValidator {

    @Override
    public void validate(Account account) throws AccountValidationException {
        if (account == null || isEmpty(account.getUsername()) || isEmpty(account.getPassword())) {
            throw new AccountValidationException("username and password cannot be empty");
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
