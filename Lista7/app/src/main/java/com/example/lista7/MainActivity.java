package com.example.lista7;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BigInteger factorialRes;
    EditText userNumber;
    TextView showResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNumber = findViewById(R.id.user_number);
        showResult = findViewById(R.id.show_result);
    }

    public int[] split(int number, int parts) {
        int[] buckets = new int[parts];
        if(number < parts)
            return buckets;
        else if (number % parts == 0)
        {
            for(int i = 0; i < parts; i++)
            {
                buckets[i] = number/parts;
            }
        }
        else
        {
            int zp = parts - (number % parts);
            int pp = number / parts;
            for(int i = 0; i < parts; i++)
            {
                if(i >= zp)
                    buckets[i] = pp + 1;
                else
                    buckets[i] = pp;
            }
        }
        return buckets;
    }

    @SuppressLint("SetTextI18n")
    public void countFactorial(View view) {

        int inputNumber = Integer.parseInt(userNumber.getText().toString());
        int numberOfThreads = inputNumber < 20 ? 1 : Runtime.getRuntime().availableProcessors();
        int begin = 1;
        factorialRes = BigInteger.valueOf(1);

        List<Factorial> threads = new ArrayList<Factorial>();
        int[] buckets = split(inputNumber, numberOfThreads);

        for(int i = 0; i < numberOfThreads; i++){
            System.out.println("BEGIN " + i + " : " + begin + " : " + buckets[i] + " : " + (begin + buckets[i] - 1));
            threads.add(new Factorial(begin, begin + buckets[i] - 1));
            begin += buckets[i];

            threads.get(i).start();
        }

        for(int i = 0; i < numberOfThreads; i++){
            try{
                threads.get(i).join();
            }
            catch (InterruptedException e){
                threads.get(i).interrupt();
            }
        }

        for(int i = 0; i < numberOfThreads; i++){
            factorialRes = factorialRes.multiply(threads.get(i).getRes());
        }

        showResult.setText(factorialRes.toString());
    }
}