package com.signup.service;

import com.signup.domain.Account;
import com.signup.domain.dao.AccountDAO;
import com.signup.service.account.SimpleAccountService;
import com.signup.service.register.AccountValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAccountServiceTest {

    public static final String JOHNY_EMAIL = "johny@mydomain.com";
    @InjectMocks
    private SimpleAccountService accountService = new SimpleAccountService();

    @Mock
    private AccountValidator accountValidator;
    @Mock
    private AccountDAO accountDAO;
    @Mock
    private EmailService emailService;

    private Account account = new Account(JOHNY_EMAIL, "secretpassword");

    @Test
    public void shouldRegisterAccount() {
        // given

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
        given(accountDAO.exists(JOHNY_EMAIL)).willReturn(true);

        // when
        accountService.delete(JOHNY_EMAIL);

        // then
        verify(accountDAO).exists(JOHNY_EMAIL);
        verify(accountDAO).remove(JOHNY_EMAIL);
    }

}
