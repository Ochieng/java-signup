package com.github.signup.service.account.password;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SecureRandomPasswordGeneratorTest {

    private SecureRandomPasswordGenerator passwordService = new SecureRandomPasswordGenerator();

    @Test
    public void testGenerate() throws Exception {
        // given

        // when
        String passwordGenerated = passwordService.generate();

        // then
        assertTrue(passwordGenerated.length() > 0);
    }
}
