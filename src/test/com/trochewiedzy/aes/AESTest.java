package com.trochewiedzy.aes;

import static org.junit.Assert.*;

public class AESTest {

    @org.junit.Test
    public void keyExpansion128b() {
        int[][] key = new int[][]{
                {0x2b, 0x28, 0xab, 0x09},
                {0x7e, 0xae, 0xf7, 0xcf},
                {0x15, 0xd2, 0x15, 0x4f},
                {0x16, 0xa6, 0x88, 0x3c},
        };

        AES aes = new AES(key);

        int[] roundKeys = aes.getRoundKeys();

        assertEquals(0x2b7e1516, roundKeys[0]);
        assertEquals(0x28aed2a6, roundKeys[1]);
        assertEquals(0xabf71588, roundKeys[2]);
        assertEquals(0x09cf4f3c, roundKeys[3]);
        assertEquals(0xa0fafe17, roundKeys[4]);
        assertEquals(0x88542cb1, roundKeys[5]);
        assertEquals(0x23a33939, roundKeys[6]);
        assertEquals(0x2a6c7605, roundKeys[7]);
        assertEquals(0xf2c295f2, roundKeys[8]);
        assertEquals(0x7a96b943, roundKeys[9]);
        assertEquals(0x5935807a, roundKeys[10]);
        assertEquals(0x7359f67f, roundKeys[11]);
        assertEquals(0x3d80477d, roundKeys[12]);
        assertEquals(0x4716fe3e, roundKeys[13]);
        assertEquals(0x1e237e44, roundKeys[14]);
        assertEquals(0x6d7a883b, roundKeys[15]);
        assertEquals(0xef44a541, roundKeys[16]);
        assertEquals(0xa8525b7f, roundKeys[17]);
        assertEquals(0xb671253b, roundKeys[18]);
        assertEquals(0xdb0bad00, roundKeys[19]);
        assertEquals(0xd4d1c6f8, roundKeys[20]);
        assertEquals(0x7c839d87, roundKeys[21]);
        assertEquals(0xcaf2b8bc, roundKeys[22]);
        assertEquals(0x11f915bc, roundKeys[23]);
        assertEquals(0x6d88a37a, roundKeys[24]);
        assertEquals(0x110b3efd, roundKeys[25]);
        assertEquals(0xdbf98641, roundKeys[26]);
        assertEquals(0xca0093fd, roundKeys[27]);
        assertEquals(0x4e54f70e, roundKeys[28]);
        assertEquals(0x5f5fc9f3, roundKeys[29]);
        assertEquals(0x84a64fb2, roundKeys[30]);
        assertEquals(0x4ea6dc4f, roundKeys[31]);
        assertEquals(0xead27321, roundKeys[32]);
        assertEquals(0xb58dbad2, roundKeys[33]);
        assertEquals(0x312bf560, roundKeys[34]);
        assertEquals(0x7f8d292f, roundKeys[35]);
        assertEquals(0xac7766f3, roundKeys[36]);
        assertEquals(0x19fadc21, roundKeys[37]);
        assertEquals(0x28d12941, roundKeys[38]);
        assertEquals(0x575c006e, roundKeys[39]);
        assertEquals(0xd014f9a8, roundKeys[40]);
        assertEquals(0xc9ee2589, roundKeys[41]);
        assertEquals(0xe13f0cc8, roundKeys[42]);
        assertEquals(0xb6630ca6, roundKeys[43]);
    }

    @org.junit.Test
    public void encryption128b() {
        int[][] key = new int[][]{
                {0x2b, 0x28, 0xab, 0x09},
                {0x7e, 0xae, 0xf7, 0xcf},
                {0x15, 0xd2, 0x15, 0x4f},
                {0x16, 0xa6, 0x88, 0x3c},
        };

        int[][] input = new int[][]{
                {0x32, 0x88, 0x31, 0xe0},
                {0x43, 0x5a, 0x31, 0x37},
                {0xf6, 0x30, 0x98, 0x07},
                {0xa8, 0x8d, 0xa2, 0x34}
        };

        AES aes = new AES(key);

        int[][] out = aes.encrypt(input);

        for (int i = 0; i < 4; i++) {
            System.out.printf("%x %x %x %x\n", out[i][0], out[i][1], out[i][2], out[i][3]);
        }

        assertEquals(0x39, out[0][0]);
        assertEquals(0x25, out[1][0]);
        assertEquals(0x84, out[2][0]);
        assertEquals(0x1d, out[3][0]);
        assertEquals(0x02, out[0][1]);
        assertEquals(0xdc, out[1][1]);
        assertEquals(0x09, out[2][1]);
        assertEquals(0xfb, out[3][1]);
        assertEquals(0xdc, out[0][2]);
        assertEquals(0x11, out[1][2]);
        assertEquals(0x85, out[2][2]);
        assertEquals(0x97, out[3][2]);
        assertEquals(0x19, out[0][3]);
        assertEquals(0x6a, out[1][3]);
        assertEquals(0x0b, out[2][3]);
        assertEquals(0x32, out[3][3]);
    }

    @org.junit.Test
    public void rotWord() {
        assertEquals(0xbbccddaa, AES.RotWord(0xaabbccdd));
        assertEquals(0x03020aaf, AES.RotWord(0xaf03020a));
    }

    @org.junit.Test
    public void sbox() {
        assertEquals(0x637c777b, AES.SubWord(0x00010203));
    }
}