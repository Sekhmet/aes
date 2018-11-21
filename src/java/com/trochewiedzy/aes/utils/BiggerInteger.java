package com.trochewiedzy.aes.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BiggerInteger {
    ArrayList<Integer> data = new ArrayList<>();

    BiggerInteger(String input) {
        for (int i = input.length() - 1; i >= 0; i--) {
            data.add(input.charAt(i) - 48);
        }
    }

    BiggerInteger Add(BiggerInteger a) {
        BiggerInteger bigger;
        BiggerInteger smaller;

        if (size() > a.size()) {
            bigger = this;
            smaller = a;
        } else {
            bigger = a;
            smaller = this;
        }

        int i = 0;
        int carry = 0;

        while (i < bigger.size() || carry != 0) {
            int res = bigger.tryGet(i) + smaller.tryGet(i) + carry;

            carry = 0;

            trySet(i, res % 10);

            if (res > 9) {
                carry = 1;
            }

            i++;
        }

        return this;
    }

    BiggerInteger Subtract(BiggerInteger a) {
        int i = 0;

        int carry = 0;

        while (i < data.size() || carry != 0) {
            int res = tryGet(i) - carry - a.tryGet(i);

            carry = 0;

            if (res < 0) {
                carry = 1;
                trySet(i, res + 10);
            } else {
                trySet(i, res);
            }

            i++;
        }

        return this;
    }

    BiggerInteger Multiply(BiggerInteger a) {
        int[] result = new int[size() + a.size()];

        for (int j = 0; j < a.size(); j++) {
            int carry = 0;

            for (int i = 0; i < size(); i++) {
                int at = i + j;

                result[at] += carry + tryGet(i) * a.tryGet(j);
                carry = result[at] / 10;
                result[at] = result[at] % 10;
            }

            result[j + size()] += carry;
        }

        data.clear();

        for (int i = 0; i < result.length; i++) {
            data.add(result[i]);
        }

        for (int i = size() - 1; i >= 0; i--) {
            if (data.get(i) != 0) break;

            data.remove(i);
        }

        return this;
    }

    int size() {
        return data.size();
    }

    int tryGet(int i) {
        return tryGet(i, 0);
    }

    int tryGet(int i, int defaultValue) {
        return i < size() ? data.get(i) : defaultValue;
    }

    void trySet(int i, int value) {
        if (i < size()) {
            data.set(i, value);
        } else {
            data.add(value);
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = data.size() -1; i >= 0; i--) {
            builder.append((char) (data.get(i) + 48));
        }

        return builder.toString();
    }
}
