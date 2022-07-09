package com.example.calculator;

public class SubtractionOperation implements Operation {
    public static final String STRING = "minus";

    @Override
    public int perform(int leftOperand, int rightOperand) {

        return Math.subtractExact(leftOperand, rightOperand);
    }

    @Override
    public String toString() {
        return STRING;
    }
}
