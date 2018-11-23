package com.trochewiedzy.pk.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

public class ElGamalEncryption {
    public static final BigInteger TWO = new BigInteger("2");

    public static final int KEY_SIZE = 128;

    private SecureRandom sc;
    private BigInteger p;
    private BigInteger g;

    private BigInteger a;
    private BigInteger b;


    ElGamalEncryption() {
        sc = new SecureRandom();
        p = BigInteger.probablePrime(8 * KEY_SIZE, sc);

        g = getGenerator(p);
        a = getRandom(p.subtract(BigInteger.ONE));
        b = g.modPow(a, p);
    }

    BigInteger[] encrypt(byte[] input) {
        BigInteger[] output = new BigInteger[2];

        BigInteger M = new BigInteger(input);

        BigInteger k = getRandomPrime(p.subtract(BigInteger.ONE));

        output[0] = g.modPow(k, p);
        output[1] = M.multiply(b.modPow(k, p)).mod(p);

        return output;
    }

    byte[] decrypt(BigInteger[] input) {
        BigInteger c1 = input[0];
        BigInteger c2 = input[1];

        return c2.multiply(c1.modPow(a, p).modInverse(p)).mod(p).toByteArray();
    }

    BigInteger getRandom(BigInteger n) {
        BigInteger r;

        do {
            r = new BigInteger(n.bitLength(), sc);
        } while (r.compareTo(n) >= 0);

        return r;
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
