package com.github.signup.service.account;

import com.github.signup.domain.Account;
import com.github.signup.service.account.exception.AccountValidationException;

public interface AccountValidator {

    void validate(Account account) throws AccountValidationException;

}
