package com.github.signup.service.account.password;

import com.github.signup.service.account.PasswordService;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class SecureRandomPasswordGenerator implements PasswordService {

    private Random random = new SecureRandom();
    private int maxBitsNumber = 130;
    private int radix = 32;

    @Override
    public String generate() {
        return new BigInteger(maxBitsNumber, random).toString(radix);
    }

    void setRandom(Random random) {
        this.random = random;
    }

    void setMaxBitsNumber(int maxBitsNumber) {
        this.maxBitsNumber = maxBitsNumber;
    }

    void setRadix(int radix) {
        this.radix = radix;
    }
}
