package com.example.starquizz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    String userName;
    int score, questionCount;
    HighscoreClass scoreList;
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

        textViewScore = (TextView) findViewById(R.id.textViewResultScore);
        textViewQuestionNumber = (TextView) findViewById(R.id.textViewResultPhrase);
        buttonResultBackMenu = findViewById(R.id.buttonResultBackMenu);
        resultIntent = getIntent();

        //on récupere le pseudo et le score du quizz
        userName = resultIntent.getStringExtra("userName");
        score = resultIntent.getIntExtra("score", -404);
        questionCount = resultIntent.getIntExtra("questionCount", -404);

        textViewQuestionNumber.setText("Good job " + userName + " you finished");
        textViewScore.setText(score + " / " + questionCount);

        //on sauvegarde le score et le pseudo avec Sugar ORM
        scoreList = new HighscoreClass(userName, score);
        scoreList.save();

        buttonResultBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMenuIntent = new Intent(v.getContext(), MenuActivity.class);
                startActivity(BackToMenuIntent);
            }
        });
    }
}
