package com.example.starquizz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Highscore extends Fragment {

    Button buttonBackHome;
    MenuFrag menuFrag;

    public Highscore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_highscore, container, false);
        buttonBackHome = view.findViewById(R.id.buttonBackHome);

        buttonBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFrag = new MenuFrag();
                getFragmentManager().beginTransaction().replace(R.id.container, menuFrag).commit();
            }
        });

        return view;
    }

}
