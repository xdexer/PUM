package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    private String currentQuestion;
    private String[] answers;
    private int currentID, answerGiven;
    private boolean buttonClicked;
    public static int cheatCounter;
    private TextView questionView, answerView;
    private Button showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();

        buttonClicked = false; //pretty useless, but no time for better way
        currentQuestion = intent.getStringExtra("question");
        currentID = intent.getIntExtra("questionID", 0);
        answerGiven = intent.getIntExtra("answerGiven", -1);
        answers = getResources().getStringArray(R.array.answers_array);
        showButton = (Button) findViewById(R.id.answer_button);


        //I hate saving state
        if(savedInstanceState != null){
            currentQuestion = savedInstanceState.getString("currentQuestion");
            currentID = savedInstanceState.getInt("currentID");
            answerGiven = savedInstanceState.getInt("answerGiven");
            cheatCounter = savedInstanceState.getInt("cheatCounter");
            buttonClicked = savedInstanceState.getBoolean("buttonClicked");
        }

        questionView = findViewById(R.id.show_question);
        questionView.setText(currentQuestion);
        answerView = findViewById(R.id.show_answer);

        //check if user already answered
        if(answerGiven != -1 || buttonClicked){
            answerView.setText(answers[currentID]);
            showButton.setEnabled(false);
        }
    }

    public void showAnswer(View view) {
        answerView.setText(answers[currentID]);
        showButton.setEnabled(false);
        if(answerGiven == -1) //unnecessary but leaving as I don't have time to debug later
            cheatCounter++;
            buttonClicked = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("currentQuestion", currentQuestion);
        outState.putInt("currentID", currentID);
        outState.putInt("answerGiven", answerGiven);
        outState.putInt("cheatCounter", cheatCounter);
        outState.putBoolean("buttonClicked", buttonClicked);
    }
}