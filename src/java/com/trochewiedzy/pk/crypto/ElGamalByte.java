package com.trochewiedzy.pk.crypto;


import java.math.BigInteger;
import java.util.Arrays;

import static com.trochewiedzy.pk.crypto.ElGamalEncryption.KEY_SIZE;

public class ElGamalByte {
    ElGamalEncryption elGamal = new ElGamalEncryption();

    byte[] encrypt(byte[] input) {
        int length = input.length / KEY_SIZE + 1;

        int at = 0;
        byte[] output = new byte[length * 2 * KEY_SIZE];

        for (int i = 0; i < length; i++) {
            byte[] slice = Arrays.copyOfRange(input, i * KEY_SIZE, (i + 1) * KEY_SIZE);

            BigInteger[] sliceCiphertext = elGamal.encrypt(slice);
            byte[] c1 = sliceCiphertext[0].toByteArray();
            byte[] c2 = sliceCiphertext[1].toByteArray();

            System.out.println(i);

            for (int j = 0; j < KEY_SIZE; j++) {
                output[at] = 1;
                output[at + KEY_SIZE] = 1;

                at++;
            }

        }

        return output;
    }

    byte[] decrypt(byte[] input) {
        int length = input.length / KEY_SIZE;

        int at = 0;
        byte[] output = new byte[input.length / 2];

        for (int i = 0; i < length; i += 2) {
            // make sure that is splits data properly.
            BigInteger c1 = new BigInteger(Arrays.copyOfRange(input, i * KEY_SIZE, (i + 1) * KEY_SIZE));
            BigInteger c2 = new BigInteger(Arrays.copyOfRange(input, (i + 1) * KEY_SIZE, (i + 2) * KEY_SIZE));

            BigInteger[] sliceInput = { c1, c2 };

            byte[] M = elGamal.decrypt(sliceInput);

            for (int j = 0; j < KEY_SIZE; j++) {
                output[at++] = M[j];
            }
        }

        return Arrays.copyOfRange(output, 0, 1);
    }
}
