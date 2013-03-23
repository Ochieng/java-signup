package com.github.signup.service.email;

import com.github.signup.service.EmailService;

public class DoNothingEmailService implements EmailService {

    @Override
    public void send(String email, String message) {
        // do nothing
    }
}
