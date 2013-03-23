singup
======

Module allows to execute basic operations on account to provide singup-panel in your application

## Operations:
1. register new account
2. send welcome message via email
3. delete existing account
4. authenticate
5. update account
6. reset password for the account
7. send custom email message

## User cases:
[1] register new account
```
validate--[OK]-->if exists--[not]-->addAccount-->sendWelcomeEmail
```

[3] delete existing account
```
if exists--[yes]-->removeAccount
```

[5] update account
```
validate--[OK]-->if exists--[yes]-->updateAccount
```

[6] reset password

User clicks forget password, email is generated, sent on the email with link to reset password. Reseting password trigger this point

```
if exists--[yes]-->generateNewPassword->updateAccount
```

--
