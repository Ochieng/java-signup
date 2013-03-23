package com.signup.service.account;

import com.signup.domain.Account;
import com.signup.domain.dao.AccountDAO;
import com.signup.service.AccountService;
import com.signup.service.EmailService;
import com.signup.service.account.exception.AccountExistsException;
import com.signup.service.account.exception.AccountNotFoundException;
import com.signup.service.register.AccountValidator;

public class SimpleAccountService implements AccountService {

    private AccountValidator accountValidator;
    private AccountDAO accountDAO;
    private EmailService emailService;

    @Override
    public void register(Account account) {
        validate(account);
        addAccount(account);
        sendWelcomeEmail(account.getUsername(), "Welcome message");
    }

    private void validate(Account account) {
        accountValidator.validate(account);
        accountShouldNotExist(account.getUsername());
    }

    private void accountShouldNotExist(String username) {
        if (accountDAO.exists(username)) {
            throw new AccountExistsException(username);
        }
    }

    private void sendWelcomeEmail(String email, String s) {
        emailService.send(email, s);
    }

    private void addAccount(Account account) {
        accountDAO.add(account);
    }

    @Override
    public void delete(String username) {
        accountShouldExist(username);
        removeAccount(username);
    }

    private void accountShouldExist(String username) {
        if (!accountDAO.exists(username)) {
            throw new AccountNotFoundException(username);
        }
    }

    private void removeAccount(String username) {
        accountDAO.remove(username);
    }

    @Override
    public boolean login(Account account) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resetPassword(String username) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Account account) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    void setAccountValidator(AccountValidator accountValidator) {
        this.accountValidator = accountValidator;
    }

    void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
