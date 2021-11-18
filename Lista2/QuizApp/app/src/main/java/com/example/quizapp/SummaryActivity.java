package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    private int userScore, correctAnswers, cheatTimes;
    private TextView summaryView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Intent intent = getIntent();

        userScore = intent.getIntExtra("userScore", 0);
        correctAnswers = intent.getIntExtra("correctAnswers", 0);
        cheatTimes = intent.getIntExtra("cheatTimes", 0);

        summaryView = findViewById(R.id.show_summary);

        if(savedInstanceState != null){
            userScore = savedInstanceState.getInt("userScore");
            correctAnswers = savedInstanceState.getInt("correctAnswers");
            cheatTimes = savedInstanceState.getInt("cheatTimes");
        }
        showSummary();
    }

    protected void showSummary()
    {
        String summary = getResources().getString(R.string.summary);

        summaryView.setText(Html.fromHtml("<h3>" + summary + "</h3> <br> <p>Points : " +
                String.valueOf(userScore) +"</p>" + "<p>Correct: " + String.valueOf(correctAnswers) +
                "</p> <p>Incorrect: " + String.valueOf(10 - correctAnswers) + "</p> <p>Times cheated: " +
                String.valueOf(cheatTimes)+ "</p>", 0));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("userScore", userScore);
        outState.putInt("correctAnswers", correctAnswers);
        outState.putInt("cheatTimes", cheatTimes);
    }

    public void restart_game(View view) {
        Intent newActivity = new Intent(SummaryActivity.this, MainActivity.class);
        //clearing activity
        newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SummaryActivity.this.startActivity(newActivity);
        finish();
    }
}