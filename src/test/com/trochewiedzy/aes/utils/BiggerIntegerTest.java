package com.trochewiedzy.aes.utils;

import org.junit.Assert;

import java.util.ArrayList;

public class BiggerIntegerTest {
    @org.junit.Test
    public void createsBiggerIntegerCorrecltyFromString() {
        BiggerInteger a = new BiggerInteger("01234567899");

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(9);
        expected.add(9);
        expected.add(8);
        expected.add(7);
        expected.add(6);
        expected.add(5);
        expected.add(4);
        expected.add(3);
        expected.add(2);
        expected.add(1);
        expected.add(0);

        Assert.assertEquals(expected, a.data);
    }

    @org.junit.Test
    public void returnStringRepresentation() {
        String input = "3120321578125782915219051829051820521";

        BiggerInteger a = new BiggerInteger(input);

        Assert.assertEquals(input, a.toString());
    }

    @org.junit.Test
    public void addsTwoBiggerIntegersWithoutCarry() {
        BiggerInteger a = new BiggerInteger("13454321");
        BiggerInteger b = new BiggerInteger("23445646");

        a.Add(b);

        Assert.assertEquals("36899967", a.toString());
    }

    @org.junit.Test
    public void addsTwoBiggerIntegersWithSimpleCarry() {
        BiggerInteger a = new BiggerInteger("127");
        BiggerInteger b = new BiggerInteger("245");

        a.Add(b);

        Assert.assertEquals("372", a.toString());
    }

    @org.junit.Test
    public void addsTwoBiggerIntegersWithCarry() {
        BiggerInteger a = new BiggerInteger("2136969219412");
        BiggerInteger b = new BiggerInteger("224166222544");

        a.Add(b);

        Assert.assertEquals("2361135441956", a.toString());
    }

    @org.junit.Test
    public void subtractsTwoBiggerIntegersWithoutCarry() {
        BiggerInteger a = new BiggerInteger("987654321");
        BiggerInteger b = new BiggerInteger("876543210");

        a.Subtract(b);

        Assert.assertEquals("111111111", a.toString());
    }

    @org.junit.Test
    public void subtractsTwoBiggerIntegersWithCarry() {
        BiggerInteger a = new BiggerInteger("2136969219412");
        BiggerInteger b = new BiggerInteger("204166222544");

        a.Subtract(b);

        Assert.assertEquals("1932802996868", a.toString());
    }

    @org.junit.Test
    public void multiplyTwoBiggerIntegers() {
        BiggerInteger a = new BiggerInteger("204166222544");
        BiggerInteger b = new BiggerInteger("2136969219412");

        a.Multiply(b);

        Assert.assertEquals("436296933220148356824128", a.toString());
    }
}