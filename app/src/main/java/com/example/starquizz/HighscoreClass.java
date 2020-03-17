package com.example.starquizz;

import com.orm.SugarRecord;

public class HighscoreClass extends SugarRecord<HighscoreClass> {

    String userName;
    int score;

    public HighscoreClass(){}

    public HighscoreClass(String userName, int score){
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }
}