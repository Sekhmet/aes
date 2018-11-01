package com.trochewiedzy.aes.crypto;

import org.junit.Assert;

import static org.junit.Assert.*;

public class ECBTest {
    @org.junit.Test
    public void matchesBlockSize() {
        int[] key = new int[]{ 0xaa, 0xbb };
        int[] input = new int[] { 0x65, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };


        ECB ecb = new ECB(AES.Type.KEY_128, key);

        int[] encrypted = ecb.encrypt(input);

        int[] plaintext = ecb.decrypt(encrypted);


        Assert.assertArrayEquals(input, plaintext);
    }

}