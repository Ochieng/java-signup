package com.github.signup.service.account;

import com.github.signup.domain.Account;
import com.github.signup.domain.dao.AccountDAO;
import com.github.signup.service.AccountService;
import com.github.signup.service.EmailService;
import com.github.signup.service.account.exception.AccountAlreadyExistsException;
import com.github.signup.service.account.exception.AccountNotFoundException;
import com.github.signup.service.account.password.SecureRandomPasswordGenerator;
import com.github.signup.service.account.validator.NotNullAccountValidator;
import com.github.signup.service.email.NullEmailService;

public class SimpleAccountService implements AccountService {

    private AccountValidator accountValidator = new NotNullAccountValidator();
    private AccountDAO accountDAO;
    private EmailService emailService = new NullEmailService();
    private PasswordService passwordGenerator = new SecureRandomPasswordGenerator();

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

        final String newPassword = passwordGenerator.generate();
        account.setPassword(newPassword);
        accountDAO.update(account);
    }

    @Override
    public void update(Account account) {
        validate(account);
        accountShouldExist(account.getUsername());

        accountDAO.update(account);
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

    void setPasswordGenerator(PasswordService passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }
}
