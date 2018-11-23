package com.trochewiedzy.pk.crypto2;

class SignedMutableBigNumber extends MutableBigNumber {

    int sign = 1;
    SignedMutableBigNumber() {
        super();
    }

    SignedMutableBigNumber(int val) {
        super(val);
    }

    SignedMutableBigNumber(MutableBigNumber val) {
        super(val);
    }

    void signedAdd(SignedMutableBigNumber addend) {
        if (sign == addend.sign)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

    void signedAdd(MutableBigNumber addend) {
        if (sign == 1)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

    void signedSubtract(SignedMutableBigNumber addend) {
        if (sign == addend.sign)
            sign = sign * subtract(addend);
        else
            add(addend);

    }

    void signedSubtract(MutableBigNumber addend) {
        if (sign == 1)
            sign = sign * subtract(addend);
        else
            add(addend);
        if (intLen == 0)
             sign = 1;
    }

    public String toString() {
        return this.toBigNumber(sign).toString();
    }

}