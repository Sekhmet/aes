package com.trochewiedzy.pk.crypto;

import org.junit.Assert;

import java.math.BigInteger;

public class ElGamalEncryptionTest {
    @org.junit.Test
    public void encryptsAndDecryptsSmallText() {
        byte[] input = {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};

        for (int i = 0; i < 100; i++) {
            ElGamalEncryption elGamal = new ElGamalEncryption();
            BigInteger[] ciphertext = elGamal.encrypt(input);

            Assert.assertArrayEquals(input, elGamal.decrypt(ciphertext));
        }
    }
}