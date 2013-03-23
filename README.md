singup
===========
Module allows to execute basic operations on account to provide singup-panel on your application

==> Operations:
1. register new account
2. send welcome message via email
3. delete existing account
4. authenticate
5. update account
6. reset password for the account
7. send new password via email
8. send custom email message

==> User cases:
[1] register new account
validate--[OK]-->isExists--[not]-->addAccount-->sendWelcomeEmail

[3] delete existing account
isExists--[yes]-->removeAccount

[5] update account
validate--[OK]-->isExists--[yes]-->updateAccount

[6] reset password
// user clicks forget password, email is generated, sent on the email with link to reset password.
// Reseting password trigger this point
isExists--[yes]-->generateNewPassword->updateAccount->[7]sendemailWithNewPassword

