package com.trochewiedzy.pk.crypto2;

import com.trochewiedzy.pk.utils.PrivateKey;
import com.trochewiedzy.pk.utils.PublicKey;

import java.util.Random;

public class ElGamal {
    Random rand = new Random();
    ModMath modMath = new ModMath();

    public BigNumber[] encryption(BigNumber text, PublicKey publicKey) {
        BigNumber[] result = new BigNumber[2];
        do {
            BigNumber k = findK(publicKey.p.subtract(BigNumber.ONE));

            result[0] = modMath.modPow(publicKey.g, k, publicKey.p);
            result[1] = text.multiply(modMath.modPow(publicKey.b, k, publicKey.p)).mod(publicKey.p);
        } while (result[0].toByteArray().length != 128 || result[1].toByteArray().length != 128);

        return result;
    }

    public BigNumber decrypt(BigNumber[] result, PrivateKey privateKey) {
        BigNumber m = modMath.modPow(result[0], privateKey.a, privateKey.p);
        m = modMath.modInverse(m, privateKey.p);

        return result[1].multiply(m).mod(privateKey.p);
    }

    public BigNumber findK(BigNumber p) {
        BigNumber k;

        do {
            k = new BigNumber(p.bitLength(), rand);
        } while (k.compareMagnitude(p) > 0 || k.gcd(p).compareTo(BigNumber.ONE) != 0);

        return k;
    }

}
