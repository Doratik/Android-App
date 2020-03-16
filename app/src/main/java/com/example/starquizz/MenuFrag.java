package com.example.starquizz;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuFrag extends Fragment {

    EditText editUserName ;
    int score;
    int questionCount;
    Intent startQuizzIntent, launchDocumentation;
    Button buttonStartQuizz, buttonAPI, buttonLeft;
    Uri docUrl;
    HighscoreFrag highscore;

    public MenuFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_menu, container, false);
        buttonStartQuizz = view.findViewById(R.id.buttonStartQuizz);
        buttonAPI = view.findViewById(R.id.buttonAPI);
        buttonLeft = view.findViewById(R.id.buttonLeft);
        editUserName = (EditText)view.findViewById(R.id.editUserName);

        buttonStartQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                questionCount = 1;
                startQuizzIntent = new Intent(getContext(), Question.class);
                startQuizzIntent.putExtra("userName", editUserName.getText().toString());
                startQuizzIntent.putExtra("questionCount", questionCount);
                startQuizzIntent.putExtra("score", score);
                //Log.i("Pseudo", editPseudoObject.getText().toString());
                if( !editUserName.getText().toString().equals("")){
                    startActivity(startQuizzIntent);
                }
                else{
                    Toast.makeText(getContext(),"Choose a nickname", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docUrl = Uri.parse("https://swapi.co/documentation");
                launchDocumentation = new Intent(Intent.ACTION_VIEW, docUrl);
                startActivity(launchDocumentation);
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highscore = new HighscoreFrag();
                getFragmentManager().beginTransaction().replace(R.id.container, highscore).commit();
            }
        });

        return view;
    }
}
