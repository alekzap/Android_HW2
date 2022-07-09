package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private Float lastX = null;
    private Float lastY = null;
    private LinearLayout layout = null;
    private final double MIN_LENGTH = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (LinearLayout) this.findViewById(R.id.ll);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                if (action == MotionEvent.ACTION_UP)
                {
                    layout.setBackgroundResource(R.color.white);
                    lastX = null;
                    lastY = null;
                    return true;
                }

                if (action != MotionEvent.ACTION_MOVE)
                    return true;

                float newX = motionEvent.getX(), newY = motionEvent.getY();
                if (lastX != null && lastY != null)
                {
                    float deltaX = Math.abs(newX - lastX);
                    float deltaY = Math.abs(newY - lastY);

                    double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    if (length < MIN_LENGTH)
                        return true;

                    if (deltaX > 3.0 * deltaY)
                        layout.setBackgroundResource(R.color.green);
                    else if (deltaY > 3.0 * deltaX)
                        layout.setBackgroundResource(R.color.yellow);
                    else
                        layout.setBackgroundResource(R.color.red);

                }

                lastX = newX;
                lastY = newY;
                return true;
            }
        });

    }
}