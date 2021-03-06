package com.github.signup.service.account;

import com.github.signup.domain.Account;
import com.github.signup.domain.dao.AccountDAO;
import com.github.signup.service.EmailService;
import com.github.signup.service.account.exception.AccountAlreadyExistsException;
import com.github.signup.service.account.exception.AccountException;
import com.github.signup.service.account.exception.AccountNotFoundException;
import com.github.signup.service.account.exception.AccountValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAccountServiceTest {

    private static final String JOHNY_EMAIL = "johny@mydomain.com";
    private static final String JOHNY_PASSWORD = "secretpassword";

    @Mock
    private AccountValidator accountValidator;
    @Mock
    private AccountDAO accountDAO;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordService passwordGenerator;

    @InjectMocks
    private final SimpleAccountService accountService = new SimpleAccountService(accountDAO);

    private final Account account = new Account(JOHNY_EMAIL, JOHNY_PASSWORD);

    @Before
    public void setup() {
        given(accountDAO.load(JOHNY_EMAIL)).willReturn(account);
        given(accountDAO.exists(JOHNY_EMAIL)).willReturn(true);
    }

    @Test
    public void shouldRegisterAccount() {
        // given
        given(accountDAO.exists(JOHNY_EMAIL)).willReturn(false);

        // when
        accountService.register(account);

        // then
        verify(accountValidator).validate(account);
        verify(accountDAO).exists(JOHNY_EMAIL);
        verify(emailService).send(eq(JOHNY_EMAIL), anyString());
        verify(accountDAO).add(account);
    }

    @Test
    public void shouldRemoveAccount() {
        // given

        // when
        accountService.delete(JOHNY_EMAIL);

        // then
        verify(accountDAO).exists(JOHNY_EMAIL);
        verify(accountDAO).remove(JOHNY_EMAIL);
    }

    @Test
    public void shouldAuthenticateForCorrectCredentials() {
        // given
        given(accountDAO.exists(JOHNY_EMAIL, JOHNY_PASSWORD)).willReturn(true);

        // when
        boolean isLoggedIn = accountService.authenticate(JOHNY_EMAIL, JOHNY_PASSWORD);

        // then
        assertTrue(isLoggedIn);
    }

    @Test
    public void shouldNotAuthenticateForIncorrectCredentials() {
        // given
        given(accountDAO.exists(JOHNY_EMAIL, JOHNY_PASSWORD)).willReturn(false);

        // when
        boolean isLoggedIn = accountService.authenticate(JOHNY_EMAIL, JOHNY_PASSWORD);

        // then
        assertFalse(isLoggedIn);
    }

    @Test
    public void shouldResetPassword() {
        // given
        final String newPassword = "1023812038";
        given(passwordGenerator.generate()).willReturn(newPassword);

        // when
        accountService.resetPassword(JOHNY_EMAIL);

        // then
        verify(accountDAO).exists(JOHNY_EMAIL);
        verify(passwordGenerator).generate();
        verify(accountDAO).update(account);

        assertEquals(newPassword, account.getPassword());
    }

    @Test
    public void shouldUpdateAccount() {
        // given

        // when
        accountService.update(account);

        // then
        verify(accountDAO).update(account);
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void shouldNotRegisterNewAccountWhenAlreadyExists() {
        // given

        // when
        accountService.register(account);

        // then
    }

    @Test(expected = AccountValidationException.class)
    public void shouldNotResetPasswordForIncorrectAccount() {
        // given
        final String newPassword = "1023812038";
        given(passwordGenerator.generate()).willReturn(newPassword);
        doThrow(new AccountValidationException()).when(accountValidator).validate(account);

        // when
        accountService.resetPassword(JOHNY_EMAIL);

        // then
    }

    @Test(expected = AccountException.class)
    public void shouldThrowExceptionForIncorrectAccount() {
        // given
        doThrow(new AccountException()).when(accountValidator).validate(account);

        // when
        accountService.update(account);

        // then

    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldNotDeleteWhenAccountDoesNotExist() {
        // given
        given(accountDAO.exists(JOHNY_EMAIL)).willReturn(false);

        // when
        accountService.delete(JOHNY_EMAIL);

        // then
    }


}
