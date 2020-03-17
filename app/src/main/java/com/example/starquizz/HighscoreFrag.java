package com.example.starquizz;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;


public class HighscoreFrag extends Fragment {

    int i;
    String text;
    TextView textViewResultPhrase;
    Button buttonBackHome, buttonDev;
    MenuFrag menuFrag;

    public HighscoreFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_highscore, container, false);
        buttonBackHome = view.findViewById(R.id.buttonBackHome);
        buttonDev = view.findViewById(R.id.buttonErase);
        text = "";

        List<HighscoreClass> highscoreClass = HighscoreClass.listAll(HighscoreClass.class);

        for(HighscoreClass hs: highscoreClass){
            text += "  " + hs.getUserName() + " - " + hs.getScore() + "\n";
            }

        //textview TESTS
        textViewResultPhrase = (TextView) view.findViewById(R.id.textViewResultPhrase);
        textViewResultPhrase.setText(text);

        buttonBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFrag = new MenuFrag();
                getFragmentManager().beginTransaction().replace(R.id.container, menuFrag).commit();
            }
        });

        buttonDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighscoreClass.deleteAll(HighscoreClass.class);
            }
        });

        return view;
    }
}