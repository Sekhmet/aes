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

        AES aes = new AES(AES.Type.KEY_128, key);

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
    public void keyExpansion192b() {
        int[][] key = new int[][]{
                {0x8e, 0xda, 0xc8, 0x80, 0x62, 0x52},
                {0x73, 0x0e, 0x10, 0x90, 0xf8, 0x2c},
                {0xb0, 0x64, 0xf3, 0x79, 0xea, 0x6b},
                {0xf7, 0x52, 0x2b, 0xe5, 0xd2, 0x7b},
        };

        AES aes = new AES(AES.Type.KEY_192, key);

        int[] roundKeys = aes.getRoundKeys();

        assertEquals(0x8e73b0f7, roundKeys[0]);
        assertEquals(0xda0e6452, roundKeys[1]);
        assertEquals(0xc810f32b, roundKeys[2]);
        assertEquals(0x809079e5, roundKeys[3]);
        assertEquals(0x62f8ead2, roundKeys[4]);
        assertEquals(0x522c6b7b, roundKeys[5]);
        assertEquals(0xfe0c91f7, roundKeys[6]);
        assertEquals(0x2402f5a5, roundKeys[7]);
        assertEquals(0xec12068e, roundKeys[8]);
        assertEquals(0x6c827f6b, roundKeys[9]);
        assertEquals(0x0e7a95b9, roundKeys[10]);
        assertEquals(0x5c56fec2, roundKeys[11]);
        assertEquals(0x4db7b4bd, roundKeys[12]);
        assertEquals(0x69b54118, roundKeys[13]);
        assertEquals(0x85a74796, roundKeys[14]);
        assertEquals(0xe92538fd, roundKeys[15]);
        assertEquals(0xe75fad44, roundKeys[16]);
        assertEquals(0xbb095386, roundKeys[17]);
        assertEquals(0x485af057, roundKeys[18]);
        assertEquals(0x21efb14f, roundKeys[19]);
        assertEquals(0xa448f6d9, roundKeys[20]);
        assertEquals(0x4d6dce24, roundKeys[21]);
        assertEquals(0xaa326360, roundKeys[22]);
        assertEquals(0x113b30e6, roundKeys[23]);
        assertEquals(0xa25e7ed5, roundKeys[24]);
        assertEquals(0x83b1cf9a, roundKeys[25]);
        assertEquals(0x27f93943, roundKeys[26]);
        assertEquals(0x6a94f767, roundKeys[27]);
        assertEquals(0xc0a69407, roundKeys[28]);
        assertEquals(0xd19da4e1, roundKeys[29]);
        assertEquals(0xec1786eb, roundKeys[30]);
        assertEquals(0x6fa64971, roundKeys[31]);
        assertEquals(0x485f7032, roundKeys[32]);
        assertEquals(0x22cb8755, roundKeys[33]);
        assertEquals(0xe26d1352, roundKeys[34]);
        assertEquals(0x33f0b7b3, roundKeys[35]);
        assertEquals(0x40beeb28, roundKeys[36]);
        assertEquals(0x2f18a259, roundKeys[37]);
        assertEquals(0x6747d26b, roundKeys[38]);
        assertEquals(0x458c553e, roundKeys[39]);
        assertEquals(0xa7e1466c, roundKeys[40]);
        assertEquals(0x9411f1df, roundKeys[41]);
        assertEquals(0x821f750a, roundKeys[42]);
        assertEquals(0xad07d753, roundKeys[43]);
        assertEquals(0xca400538, roundKeys[44]);
        assertEquals(0x8fcc5006, roundKeys[45]);
        assertEquals(0x282d166a, roundKeys[46]);
        assertEquals(0xbc3ce7b5, roundKeys[47]);
        assertEquals(0xe98ba06f, roundKeys[48]);
        assertEquals(0x448c773c, roundKeys[49]);
        assertEquals(0x8ecc7204, roundKeys[50]);
        assertEquals(0x01002202, roundKeys[51]);
    }

    @org.junit.Test
    public void keyExpansion256b() {
        int[][] key = new int[][]{
                {0x60, 0x15, 0x2b, 0x85, 0x1f, 0x3b, 0x2d, 0x09},
                {0x3d, 0xca, 0x73, 0x7d, 0x35, 0x61, 0x98, 0x14},
                {0xeb, 0x71, 0xae, 0x77, 0x2c, 0x08, 0x10, 0xdf},
                {0x10, 0xbe, 0xf0, 0x81, 0x07, 0xd7, 0xa3, 0xf4}
        };

        AES aes = new AES(AES.Type.KEY_256, key);

        int[] roundKeys = aes.getRoundKeys();

        assertEquals(0x603deb10, roundKeys[0]);
        assertEquals(0x15ca71be, roundKeys[1]);
        assertEquals(0x2b73aef0, roundKeys[2]);
        assertEquals(0x857d7781, roundKeys[3]);
        assertEquals(0x1f352c07, roundKeys[4]);
        assertEquals(0x3b6108d7, roundKeys[5]);
        assertEquals(0x2d9810a3, roundKeys[6]);
        assertEquals(0x0914dff4, roundKeys[7]);
        assertEquals(0x9ba35411, roundKeys[8]);
        assertEquals(0x8e6925af, roundKeys[9]);
        assertEquals(0xa51a8b5f, roundKeys[10]);
        assertEquals(0x2067fcde, roundKeys[11]);
        assertEquals(0xa8b09c1a, roundKeys[12]);
        assertEquals(0x93d194cd, roundKeys[13]);
        assertEquals(0xbe49846e, roundKeys[14]);
        assertEquals(0xb75d5b9a, roundKeys[15]);
        assertEquals(0xd59aecb8, roundKeys[16]);
        assertEquals(0x5bf3c917, roundKeys[17]);
        assertEquals(0xfee94248, roundKeys[18]);
        assertEquals(0xde8ebe96, roundKeys[19]);
        assertEquals(0xb5a9328a, roundKeys[20]);
        assertEquals(0x2678a647, roundKeys[21]);
        assertEquals(0x98312229, roundKeys[22]);
        assertEquals(0x2f6c79b3, roundKeys[23]);
        assertEquals(0x812c81ad, roundKeys[24]);
        assertEquals(0xdadf48ba, roundKeys[25]);
        assertEquals(0x24360af2, roundKeys[26]);
        assertEquals(0xfab8b464, roundKeys[27]);
        assertEquals(0x98c5bfc9, roundKeys[28]);
        assertEquals(0xbebd198e, roundKeys[29]);
        assertEquals(0x268c3ba7, roundKeys[30]);
        assertEquals(0x09e04214, roundKeys[31]);
        assertEquals(0x68007bac, roundKeys[32]);
        assertEquals(0xb2df3316, roundKeys[33]);
        assertEquals(0x96e939e4, roundKeys[34]);
        assertEquals(0x6c518d80, roundKeys[35]);
        assertEquals(0xc814e204, roundKeys[36]);
        assertEquals(0x76a9fb8a, roundKeys[37]);
        assertEquals(0x5025c02d, roundKeys[38]);
        assertEquals(0x59c58239, roundKeys[39]);
        assertEquals(0xde136967, roundKeys[40]);
        assertEquals(0x6ccc5a71, roundKeys[41]);
        assertEquals(0xfa256395, roundKeys[42]);
        assertEquals(0x9674ee15, roundKeys[43]);
        assertEquals(0x5886ca5d, roundKeys[44]);
        assertEquals(0x2e2f31d7, roundKeys[45]);
        assertEquals(0x7e0af1fa, roundKeys[46]);
        assertEquals(0x27cf73c3, roundKeys[47]);
        assertEquals(0x749c47ab, roundKeys[48]);
        assertEquals(0x18501dda, roundKeys[49]);
        assertEquals(0xe2757e4f, roundKeys[50]);
        assertEquals(0x7401905a, roundKeys[51]);
        assertEquals(0xcafaaae3, roundKeys[52]);
        assertEquals(0xe4d59b34, roundKeys[53]);
        assertEquals(0x9adf6ace, roundKeys[54]);
        assertEquals(0xbd10190d, roundKeys[55]);
        assertEquals(0xfe4890d1, roundKeys[56]);
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

        AES aes = new AES(AES.Type.KEY_128, key);

        int[][] out = aes.encrypt(input);

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