package com.example.starquizz;

import com.orm.SugarRecord;

public class HighscoreClass extends SugarRecord<HighscoreClass> {


    int logoNum;
    String userName;
    int score;


    public HighscoreClass(){}

    public HighscoreClass(int logoNum, String userName, int score){
        this.logoNum = logoNum;
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }

    public int getLogoNum() {
        return logoNum;
    }
}