package com.example.calculator;

public class AdditionOperation implements Operation {
    public static final String STRING = "plus";

    @Override
    public int perform(int leftOperand, int rightOperand) {
        return Math.addExact(leftOperand, rightOperand);
    }

    @Override
    public String toString() {
        return STRING;
    }
}
