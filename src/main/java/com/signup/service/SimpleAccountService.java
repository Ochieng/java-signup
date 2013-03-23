package com.signup.service;

import com.signup.domain.Account;
import com.signup.domain.dao.AccountDAO;
import com.signup.service.register.AccountExistsException;
import com.signup.service.register.AccountValidator;

public class SimpleAccountService implements AccountService {

    private AccountValidator accountValidator;
    private AccountDAO accountDAO;
    private EmailService emailService;

    @Override
    public void register(Account account) {
        validate(account);
        addAccount(account);
        sendWelcomeEmail(account, "Welcome message");
    }

    private void validate(Account account) {
        accountValidator.validate(account);
        verifyIfExist(account);
    }

    private void verifyIfExist(Account account) {
        if (accountDAO.exists(account)) {
            throw new AccountExistsException(account.getUsername());
        }
    }

    private void sendWelcomeEmail(Account account, String s) {
        emailService.send(account.getUsername(), s);
    }

    private void addAccount(Account account) {
        accountDAO.add(account);
    }

    @Override
    public void delete(String username) {
        verifyIfExist(new Account(username));

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
