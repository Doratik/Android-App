package com.example.starquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    String userName;
    int score, questionCount;
    Intent resultIntent;
    TextView textViewQuestionNumber;
    TextView textViewScore;
    Button buttonResultBackMenu;
    Intent BackToMenuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        resultIntent = getIntent();
        textViewScore = (TextView) findViewById(R.id.textViewResultScore);
        buttonResultBackMenu = findViewById(R.id.buttonResultBackMenu);

        //on récupere le pseudo et le score du quizz
        userName = resultIntent.getStringExtra("userName");
        score = resultIntent.getIntExtra("score", -404);
        questionCount = resultIntent.getIntExtra("questionCount", -404);

        textViewQuestionNumber = (TextView) findViewById(R.id.textViewResultPhrase);
        textViewQuestionNumber.setText("Good job " + userName + " you did great");
        textViewScore.setText(score + " / " + questionCount);

        buttonResultBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMenuIntent = new Intent(v.getContext(), Menu.class);
                startActivity(BackToMenuIntent);
            }
        });

    }
}
