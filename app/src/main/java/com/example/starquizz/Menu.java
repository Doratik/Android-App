package com.example.starquizz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    EditText editUserName ;
    int score;
    int questionCount;
    Intent startQuizzIntent;
    Button buttonStartQuizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        buttonStartQuizz = findViewById(R.id.buttonStartQuizz);
        editUserName = (EditText)findViewById(R.id.editUserName);

        buttonStartQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                questionCount = 1;
                startQuizzIntent = new Intent(getApplicationContext(), Question.class);
                startQuizzIntent.putExtra("userName", editUserName.getText().toString());
                startQuizzIntent.putExtra("questionCount", questionCount);
                startQuizzIntent.putExtra("score", score);
                //Log.i("Pseudo", editPseudoObject.getText().toString());
                if( !editUserName.getText().toString().equals("")){
                    startActivity(startQuizzIntent);
                }
                else{
                    Toast.makeText(Menu.this,"Choose a nickname",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}