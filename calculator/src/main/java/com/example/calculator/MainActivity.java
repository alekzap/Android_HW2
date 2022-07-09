package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Calculator calculator = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.CE_button).setOnClickListener(
                view -> {
                    calculator.pressCE();
                    setText();
                }
        );

        findViewById(R.id.clear_button).setOnClickListener(
                view -> {
                    calculator.pressClear();
                    setText();
                }
        );

        findViewById(R.id.change_sign_button).setOnClickListener(
                view -> {
                    calculator.pressChangeSign();
                    setText();
                }
        );

        findViewById(R.id.divide_button).setOnClickListener(
                view -> {
                    calculator.pressOperation(new DivisionOperation());
                    setText();
                }
        );

        findViewById(R.id.multiply_button).setOnClickListener(
                view -> {
                    calculator.pressOperation(new MultiplicationOperation());
                    setText();
                }
        );

        findViewById(R.id.minus_button).setOnClickListener(
                view -> {
                    calculator.pressOperation(new SubtractionOperation());
                    setText();
                }
        );

        findViewById(R.id.plus_button).setOnClickListener(
                view -> {
                    calculator.pressOperation(new AdditionOperation());
                    setText();
                }
        );

        findViewById(R.id.equals_button).setOnClickListener(
                view -> {
                    calculator.pressEquals();
                    setText();
                }
        );

        if (savedInstanceState != null)
            calculator.restoreState(savedInstanceState);
        setText();
    }

    private void setText() {
        ((TextView) findViewById(R.id.textView)).setText(getText());
    }

    private String getText() {
        if (calculator.getIsError())
            return getResources().getString(R.string.error);

        int number = calculator.getNumber();
        if (number == 0 && calculator.getIsShowingMinus())
            return "-0";

        return String.valueOf(number);
    }

    public void onClick(View view) {
        Button button = (Button) view;
        String text = button.getText().toString();

        int number = Integer.parseInt(text);
        calculator.pressNumber(number);
        setText();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        calculator.saveState(bundle);
    }
}