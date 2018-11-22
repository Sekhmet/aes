package com.trochewiedzy.pk.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class ElGamalEncryption {
    private static final BigInteger TWO = new BigInteger("2");

    private static final int KEY_SIZE = 128;

    private SecureRandom sc;
    private BigInteger p;
    private BigInteger g;

    private BigInteger a;
    private BigInteger b;

    ElGamalEncryption() {
        sc = new SecureRandom();
        p = BigInteger.probablePrime(8 * KEY_SIZE, sc);
        BigInteger pPrime = p.subtract(BigInteger.ONE).divide(TWO);

        g = getGenerator(p);

        a = getRandom(p.subtract(BigInteger.ONE));
        b = g.modPow(a, p);

//        BigInteger[] pubKey = {b, g, p};
//        BigInteger[] privKey = {a, g, p};
    }

    byte[] encrypt(byte[] input) {
        int length = input.length / KEY_SIZE + 1;

        int at = 0;
        byte[] output = new byte[length * 2 * KEY_SIZE];

        for (int i = 0; i < length; i++) {
            BigInteger M = new BigInteger(Arrays.copyOfRange(input, i * KEY_SIZE, (i + 1) * KEY_SIZE));

//            BigInteger pPrime = p.subtract(BigInteger.ONE).divide(TWO);
//            BigInteger k = getRandomPrime(pPrime.subtract(BigInteger.ONE));
            BigInteger k = new BigInteger(p.bitCount() - 1, sc);

            byte[] c1 = g.modPow(k, p).toByteArray();
            byte[] c2 = M.multiply(b.modPow(k, p)).mod(p).toByteArray();

            for (int j = 0; j < KEY_SIZE; j++) {
                output[at] = c1[j];
                output[at + KEY_SIZE] = c2[j];

                at++;
            }

        }

        return output;
    }

    byte[] decrypt(byte[] input) {
        int length = input.length / (KEY_SIZE * 2);

        int at = 0;
        byte[] output = new byte[input.length / 2];

        for (int i = 0; i < length; i++) {
            BigInteger c1 = new BigInteger(Arrays.copyOfRange(input, i * KEY_SIZE, (i + 1) * KEY_SIZE));
            BigInteger c2 = new BigInteger(Arrays.copyOfRange(input, (i + 1) * KEY_SIZE, (i + 2) * KEY_SIZE));

            byte[] M = c2.multiply(c1.modPow(a, p).modInverse(p)).mod(p).toByteArray();

            for (int j = 0; j < KEY_SIZE; j++) {
                output[at++] = M[j];
            }
        }

        return Arrays.copyOfRange(output, 0, 1);
    }

    BigInteger getRandom(BigInteger n) {
        BigInteger result = new BigInteger(n.bitLength(), sc);

        while (result.compareTo(n) >= 0) {
            result = new BigInteger(n.bitLength(), sc);
        }

        return result;
    }

    BigInteger getRandomPrime(BigInteger n) {
        BigInteger result = BigInteger.probablePrime(n.bitLength(), sc);
        while (result.compareTo(n) >= 0) {
            result = new BigInteger(n.bitLength(), sc);
        }

        return result;
    }

    BigInteger getGenerator(BigInteger p) {
        if (p.compareTo(TWO) == 0) return BigInteger.ONE;

        BigInteger p1 = TWO;
        BigInteger p2 = p.subtract(BigInteger.ONE).divide(p1);

        while (true) {
            BigInteger g = getRandomPrime(p.subtract(BigInteger.ONE));

            if (g.modPow(p.subtract(BigInteger.ONE).divide(p1), p).compareTo(BigInteger.ONE) != 0) {
                if (g.modPow(p.subtract(BigInteger.ONE).divide(p2), p).compareTo(BigInteger.ONE) != 0) {
                    return g;
                }
            }
        }
    }
}
