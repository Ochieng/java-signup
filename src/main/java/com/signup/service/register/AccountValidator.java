package com.signup.service.register;

import com.signup.domain.Account;

public interface AccountValidator {

    void validate(Account account) throws AccountValidationException;

}
