package com.trochewiedzy.pk.crypto;

import org.junit.Assert;

public class ElGamalEncryptionTest {
    @org.junit.Test
    public void encryptsAndDecryptsSmallText() {
        String input = "1";

        ElGamalEncryption elGamal = new ElGamalEncryption();

        byte[] ciphertext = elGamal.encrypt(input.getBytes());

        Assert.assertEquals("1", new String(elGamal.decrypt(ciphertext)));
    }
}