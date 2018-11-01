package com.trochewiedzy.aes.crypto;

public class ECB {
    private static final int N = 4;
    private static final int BLOCK_SIZE = N * N;

    private AES aes;

    public ECB(AES.Type type, int[] key) {
        int Nk = 4;

        switch (type) {
            case KEY_128:
                Nk = 4;
                break;
            case KEY_192:
                Nk = 6;
                break;
            case KEY_256:
                Nk = 8;
                break;
        }

        if (key.length > Nk * 4) {
            throw new IllegalArgumentException("key is too long");
        }

        int[][] aesKey = new int[4][Nk];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nk; j++) {
                int curr = i * 4 + j;
                aesKey[j][i] = curr < key.length ? key[i * 4 + j] : 0;
            }
        }

        aes = new AES(type, aesKey);
    }

    private int getBlockCount(int[] input) {
        int quotient = input.length / BLOCK_SIZE;
        int remainder = input.length % BLOCK_SIZE;

        if (remainder == 0) return quotient;
        return quotient + 1;
    }

    public int[] encrypt(int[] input) {
        int blockCount = getBlockCount(input);

        int[] output = new int[blockCount * BLOCK_SIZE];

        for (int b = 0; b < blockCount; b++) {
            int[][] slice = new int[4][4];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int curr = b * BLOCK_SIZE + j + i * 4;

                    slice[j][i] = (curr < input.length) ? input[curr] : 0;
                }
            }

            int[][] encryptedSlice = aes.encrypt(slice);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int curr = b * BLOCK_SIZE + j + i * 4;

                    output[curr] = encryptedSlice[j][i];
                }
            }
        }

        return output;
    }

    public int[] decrypt(int[] input) {
        int[] output = new int[input.length];

        for (int b = 0; b < getBlockCount(input); b++) {
            int[][] slice = new int[4][4];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int curr = b * BLOCK_SIZE + j + i * 4;

                    slice[j][i] = (curr < input.length) ? input[curr] : 0;
                }
            }

            int[][] decryptedSlice = aes.decrypt(slice);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int curr = b * BLOCK_SIZE + j + i * 4;

                    output[curr] = decryptedSlice[j][i];
                }
            }
        }

        return output;
    }
}
