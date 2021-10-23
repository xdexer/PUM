package com.example.calculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itis.libs.parserng.android.expressParser.MathExpression;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private long result;
    private String result_text;
    private Boolean operand_switch;
    private TextView showResult;
    private static final String[] operands = {"+", "-", "*", "/"};
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showResult = findViewById(R.id.show_result);
        result_text = "";
        operand_switch = true;
        if (savedInstanceState != null) {
            result = savedInstanceState.getLong("result_state");
            result_text = savedInstanceState.getString("result_text");
            operand_switch = savedInstanceState.getBoolean("operand_switch");
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
        //case when textview is empty and user wants to put a operand first
        if(result_text.length() == 0 && Arrays.asList(operands).contains(b.getText().toString())) {
            return;
        }
        if(operand_switch && Arrays.asList(operands).contains(b.getText().toString())){
            result_text = result_text.substring(0, result_text.length() - 1);
        }

        result_text = result_text.concat(b.getText().toString());
        operand_switch = Arrays.asList(operands).contains(b.getText().toString());
        if (showResult != null) {
            showResult.setText(result_text);
        }
    }

    @SuppressLint("SetTextI18n")
    public void evaluateResult(View view)
    {
        MathExpression expr = new MathExpression(result_text);

        if(result_text.isEmpty())
            return;
        //dividing by zero
        if(expr.solve().equals("Infinity")){
            showResult.setText("Nie dziel przez 0!");
            result_text = "";
            result = 0;
            return;
        }
        //last is operand
        if(Arrays.asList(operands).contains(result_text.substring(result_text.length() - 1)))
        {
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
        outState.putBoolean("operand_switch", operand_switch);
    }
}