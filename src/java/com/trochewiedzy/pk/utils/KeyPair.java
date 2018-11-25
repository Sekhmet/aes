package com.trochewiedzy.pk.utils;

import com.trochewiedzy.pk.crypto2.BigNumber;
import com.trochewiedzy.pk.crypto2.ModMath;

import java.security.SecureRandom;
import java.util.Random;

public class KeyPair {
    SecureRandom sc = new SecureRandom();

    PrivateKey privateKey = new PrivateKey();
    PublicKey publicKey = new PublicKey();

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void generate() {
        ModMath modMath = new ModMath();
        BigNumber p = modMath.createPrime(1025, 100, new Random());
        BigNumber g = modMath.findPrimitiveRoot(p);
        BigNumber a;
        do {
            a = new BigNumber(1024, sc);

        } while(a.compareMagnitude(p) > 0);
        BigNumber b = modMath.modPow(g, a, p);

        privateKey.p = p;
        privateKey.g = g;
        privateKey.a = a;

        publicKey.p = p;
        publicKey.g = g;
        publicKey.b = b;
    }
}
