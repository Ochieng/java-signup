package com.signup.service.account;

import com.signup.domain.Account;
import com.signup.domain.dao.AccountDAO;
import com.signup.service.AccountService;
import com.signup.service.EmailService;
import com.signup.service.account.exception.AccountAlreadyExistsException;
import com.signup.service.account.exception.AccountNotFoundException;

public class SimpleAccountService implements AccountService {

    private AccountValidator accountValidator;
    private AccountDAO accountDAO;
    private EmailService emailService;
    private PasswordService passwordService;

    @Override
    public void register(Account account) {
        validate(account);
        accountShouldNotExist(account.getUsername());
        addAccount(account);
        sendWelcomeEmail(account.getUsername(), "Welcome message");
    }

    private void validate(Account account) {
        accountValidator.validate(account);
    }

    private void accountShouldNotExist(String username) {
        if (accountDAO.exists(username)) {
            throw new AccountAlreadyExistsException(username);
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
    public boolean authenticate(String username, String password) {
        return accountDAO.exists(username, password);
    }

    @Override
    public void resetPassword(String username) {
        accountShouldExist(username);
        final Account account = accountDAO.load(username);
        validate(account);

        final String newPassword = passwordService.generate();
        account.setPassword(newPassword);
        accountDAO.update(account);
    }

    @Override
    public void update(Account account) {
        validate(account);
        accountShouldExist(account.getUsername());

        accountDAO.update(account);
    }
}
