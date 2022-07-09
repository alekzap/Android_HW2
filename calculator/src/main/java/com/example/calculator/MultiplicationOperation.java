package com.example.calculator;

public class MultiplicationOperation implements Operation {
    public static final String STRING = "times";

    @Override
    public int perform(int leftOperand, int rightOperand) {
        return Math.multiplyExact(leftOperand, rightOperand);
    }

    @Override
    public String toString() {
        return STRING;
    }
}
