package com.example.calculator;

import android.os.Bundle;

public class Calculator {
    public final int MAX_NUMBER = 999999999;
    private int leftOperand = 0;
    private int rightOperand = 0;
    private Operation currentOperation = null;

    private boolean isEnteringLeftOperand = true;
    private boolean isShowingLeftOperand = true;
    private boolean isAfterEquals = false;
    private boolean isError = false;

    //поведение этого флага совпадает с поведением калькулятора на моем телефоне
    //но не совпадает с поведением калькулятора Windows
    private boolean isShowingMinus = false;

    public int getNumber() {
        return isShowingLeftOperand ? leftOperand : rightOperand;
    }

    public boolean getIsError() {
        return isError;
    }

    public boolean getIsShowingMinus() {
        return isShowingMinus;
    }

    public void pressNumber(int digit) {
        if (digit < 0 || digit > 9)
            throw new ArithmeticException("Invalid digit!");

        isError = false;
        if (isEnteringLeftOperand) {
            if (isAfterEquals) {
                leftOperand = 0;
                isAfterEquals = false;
            }

            leftOperand = updateNumber(leftOperand, digit);
            isShowingLeftOperand = true;
        }
        else {
            rightOperand = updateNumber(rightOperand, digit);
            isShowingLeftOperand = false;
        }
    }

    private int updateNumber(int number, int digit) {
        if (String.valueOf(Math.abs(number)).length() == 9)
            return number;

        int testValue = Math.abs(number) * 10 + digit;
        if (number < 0)
            testValue = -testValue;

        if (number == 0 && isShowingMinus && testValue != 0) {
            testValue = -testValue;
            isShowingMinus = false;
        }

        return testValue;
    }

    public void pressOperation(Operation operation) {
        if (isError)
            return;

        isAfterEquals = false;
        isShowingMinus = false;
        if (isEnteringLeftOperand || isShowingLeftOperand) {
            isEnteringLeftOperand = false;
        }
        else {
            performOperation();
            if (isError)
                return;
        }

        rightOperand = 0;
        currentOperation = operation;
    }

    private void performOperation() {
        if (currentOperation == null)
            throw new NullPointerException("Operation missing!");

        try {
            int result = currentOperation.perform(leftOperand, rightOperand);
            if (Math.abs(result) > MAX_NUMBER)
                throw new ArithmeticException("Too large result!");

            leftOperand = result;
            isShowingLeftOperand = true;
        }
        catch (ArithmeticException ex) {
            isError = true;
            leftOperand = rightOperand = 0;
            isEnteringLeftOperand = true;
            isShowingLeftOperand = true;
            isAfterEquals = false;
            currentOperation = null;
            isShowingMinus = false;
        }
    }

    public void pressCE() {
        if (isError) {
            isError = false;
            return;
        }

        isShowingMinus = false;
        if (isEnteringLeftOperand) {
            leftOperand = 0;
            //currentOperation = null;
        }
        else {
            isShowingLeftOperand = false;
            rightOperand = 0;
        }
    }

    public void pressClear() {
        isError = false;
        leftOperand = rightOperand = 0;
        isEnteringLeftOperand = true;
        isShowingLeftOperand = true;
        isAfterEquals = false;
        currentOperation = null;
        isShowingMinus = false;
    }

    public void pressChangeSign() {
        if (isError)
            return;

        if (isShowingMinus) {
            isShowingMinus = false;
            return;
        }

        if (isEnteringLeftOperand) {
            leftOperand = -leftOperand;
            if (leftOperand == 0)
                isShowingMinus = true;
        }
        else {
            rightOperand = -rightOperand;
            isShowingLeftOperand = false;
            if (rightOperand == 0)
                isShowingMinus = true;
        }
    }

    public void pressEquals() {
        if (currentOperation == null)
            return;

        if (isShowingLeftOperand && !isEnteringLeftOperand)
            rightOperand = leftOperand;

        isShowingMinus = false;
        performOperation();
        if (isError)
            return;

        isAfterEquals = true;
        isEnteringLeftOperand = true;
    }

    public void saveState(Bundle bundle) {
        bundle.putBoolean("isEnteringLeftOperand", isEnteringLeftOperand);
        bundle.putBoolean("isAfterEquals", isAfterEquals);
        bundle.putBoolean("isShowingLeftOperand", isShowingLeftOperand);
        bundle.putBoolean("isError", isError);
        bundle.putBoolean("isShowingMinus", isShowingMinus);

        bundle.putInt("leftOperand", leftOperand);
        bundle.putInt("rightOperand", rightOperand);
        bundle.putString(
                "operation",
                currentOperation == null ? "null" : currentOperation.toString());
    }

    public void restoreState(Bundle bundle) {
        isEnteringLeftOperand = bundle.getBoolean("isEnteringLeftOperand");
        isAfterEquals = bundle.getBoolean("isAfterEquals");
        isShowingLeftOperand = bundle.getBoolean("isShowingLeftOperand");
        isError = bundle.getBoolean("isError");
        isShowingMinus = bundle.getBoolean("isShowingMinus");

        leftOperand = bundle.getInt("leftOperand");
        rightOperand = bundle.getInt("rightOperand");

        String operationString = bundle.getString("operation");
        switch (operationString) {
            case "null":
                currentOperation = null;
                break;
            case AdditionOperation.STRING:
                currentOperation = new AdditionOperation();
                break;
            case SubtractionOperation.STRING:
                currentOperation = new SubtractionOperation();
                break;
            case DivisionOperation.STRING:
                currentOperation = new DivisionOperation();
                break;
            case MultiplicationOperation.STRING:
                currentOperation = new MultiplicationOperation();
                break;
        }
    }
}
