package com.example.starquizz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Menu extends AppCompatActivity {

    MenuFrag menuFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        menuFrag = new MenuFrag();
        getSupportFragmentManager().beginTransaction().add(R.id.container, menuFrag).commit();
    }
}