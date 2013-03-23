package com.signup.service.account;

import com.signup.domain.Account;
import com.signup.service.account.exception.AccountValidationException;

public interface AccountValidator {

    void validate(Account account) throws AccountValidationException;

}
