package com.trochewiedzy.pk.crypto;


import org.junit.Assert;

public class ElGamalByteTest {

    @org.junit.Test
    public void encrypts() {
        ElGamalByte elGamalByte = new ElGamalByte();

        byte[] x = { 1 };

        for (int i = 0; i < 1; i++) {
            byte[] a = elGamalByte.encrypt(x);

            byte[] b = elGamalByte.decrypt(a);

            Assert.assertArrayEquals(x, b);
        }
    }

}