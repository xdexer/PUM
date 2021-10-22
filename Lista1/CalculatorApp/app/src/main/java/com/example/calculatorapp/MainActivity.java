package com.example.calculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itis.libs.parserng.android.expressParser.MathExpression;

public class MainActivity extends AppCompatActivity {

    private long result;
    private String result_text;
    private TextView showResult;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showResult = findViewById(R.id.show_result);
        result_text = "";
        if (savedInstanceState != null) {
            result = savedInstanceState.getLong("result_state");
            result_text = savedInstanceState.getString("result_text");
        }

        if (showResult != null) {
            if (result_text.equals(""))
                showResult.setText("0");
            else
                showResult.setText(result_text);
        }
    }

    public void appendResult(View view)
    {
        Button b = (Button) view;
        result_text = result_text.concat(b.getText().toString());
        if (showResult != null)
            showResult.setText(result_text);
    }

    @SuppressLint("SetTextI18n")
    public void evaluateResult(View view)
    {
        MathExpression expr = new MathExpression(result_text);
        if(expr.solve().equals("Infinity")){
            showResult.setText("Nie dziel przez 0!");
            result_text = "";
            result = 0;
            return;
        }
        result = (long) Float.parseFloat(expr.solve());
        result_text = Long.toString(result);
        if (showResult != null)
            showResult.setText(result_text);
    }

    public void clearResult(View view)
    {
        result_text = "";
        result = 0;
        if (showResult != null)
            showResult.setText("0");
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("result_state", result);
        outState.putString("result_text", result_text);
    }
}