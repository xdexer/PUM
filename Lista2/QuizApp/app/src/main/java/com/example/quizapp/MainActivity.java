package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private int userScore, currentQuestion;
    private int[] userAnswers;
    private String[] questions;
    private String[] answers;
    private TextView questionView;
    private Button trueButton, falseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionActivity.cheatCounter = 0;
        userScore = 0;
        currentQuestion = 0;
        questionView = findViewById(R.id.question_text);
        questions = getResources().getStringArray(R.array.questions_array);
        answers = getResources().getStringArray(R.array.answers_array);
        userAnswers = new int[10];

        trueButton = (Button) findViewById(R.id.button_true);
        falseButton = (Button) findViewById(R.id.button_false);

        Arrays.fill(userAnswers, -1);

        if(savedInstanceState != null){
            userScore = savedInstanceState.getInt("userScore");
            currentQuestion = savedInstanceState.getInt("currentQuestion");
            userAnswers = savedInstanceState.getIntArray("userAnswers");
            QuestionActivity.cheatCounter = savedInstanceState.getInt("cheats");
        }

        updateQuestion(currentQuestion);
        updateButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void showSummary(){
        int correctAnswers = 0;
        int cheatTimes = QuestionActivity.cheatCounter;
        for(int x = 0; x < userAnswers.length; x++){
            if(userAnswers[x] == -1) return;
            String userAnswerStr = userAnswers[x] == 1 ? "True" : "False";
            if(userAnswerStr.equals(answers[x]))
                correctAnswers++;
        }


        userScore = Math.max(((correctAnswers * 10) - (cheatTimes * 15)), 0);
        Intent newActivity = new Intent(MainActivity.this, SummaryActivity.class);
        newActivity.putExtra("userScore", userScore);
        newActivity.putExtra("correctAnswers", correctAnswers);
        newActivity.putExtra("cheatTimes", cheatTimes);
        MainActivity.this.startActivity(newActivity);
    }

    protected void updateButtons(){
        if(userAnswers[currentQuestion] == -1){
            falseButton.setEnabled(true);
            trueButton.setEnabled(true);
        }
        else{
            falseButton.setEnabled(false);
            trueButton.setEnabled(false);
        }
    }

    protected void updateQuestion(int id){
        if(questionView != null){
            questionView.setText(questions[id]);
        }
    }

    public void prevQuestion(View view) {
        currentQuestion--;
        if(currentQuestion < 0){
            currentQuestion = 9;
        }
        updateQuestion(currentQuestion);
        updateButtons();
    }

    public void nextQuestion(View view) {
        currentQuestion++;
        if(currentQuestion > 9){
            currentQuestion = 0;
        }
        updateQuestion(currentQuestion);
        updateButtons();
    }

    public void chooseFalse(View view) {
        userAnswers[currentQuestion] = 0;
        updateButtons();
        showSummary();
    }

    public void chooseTrue(View view) {
        userAnswers[currentQuestion] = 1;
        updateButtons();
        showSummary();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("userScore", userScore);
        outState.putInt("currentQuestion", currentQuestion);
        outState.putIntArray("userAnswers", userAnswers);
        outState.putInt("cheats", QuestionActivity.cheatCounter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle outState){
        super.onRestoreInstanceState(outState);
        userScore = outState.getInt("userScore");
        currentQuestion = outState.getInt("currentQuestion");
        userAnswers = outState.getIntArray("userAnswers");
        QuestionActivity.cheatCounter = outState.getInt("cheats");
        updateQuestion(currentQuestion);
        updateButtons();
    }

    public void cheatQuestion(View view) {
        Intent newActivity = new Intent(MainActivity.this, QuestionActivity.class);

        newActivity.putExtra("question", questions[currentQuestion]);
        newActivity.putExtra("questionID", currentQuestion);
        newActivity.putExtra("answerGiven", userAnswers[currentQuestion]);

        MainActivity.this.startActivity(newActivity);
    }

    public void searchQuestion(View view) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, questions[currentQuestion]);
        startActivity(intent);
    }
}