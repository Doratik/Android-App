package com.example.starquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    String userName;
    int score, questionCount;
    Intent resultIntent;
    TextView textViewQuestionNumber;
    TextView textViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        //on récupere le pseudo et le score du quizz
        resultIntent = getIntent();
        userName = resultIntent.getStringExtra("userName");
        score = resultIntent.getIntExtra("score", -404);
        questionCount = resultIntent.getIntExtra("questionCount", -404);

        textViewQuestionNumber = (TextView) findViewById(R.id.textViewResultPhrase);
        textViewQuestionNumber.setText("Good job " + userName + " you did great");

        textViewScore = (TextView) findViewById(R.id.textViewResultScore);
        textViewScore.setText(score + " / " + questionCount);
    }
}
