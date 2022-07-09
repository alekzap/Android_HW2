package com.example.calculator;

public class DivisionOperation implements Operation {
    public static final String STRING = "divide";

    @Override
    public int perform(int leftOperand, int rightOperand) {
        return leftOperand / rightOperand;
    }

    @Override
    public String toString() {
        return STRING;
    }
}
