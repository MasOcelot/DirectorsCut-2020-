package com.example.android.directorscut;

import android.widget.TextView;

public class ScoreRow {
    private String mName;
    private ScoreBox mBox1;
    private ScoreBox mBox2;
    private ScoreBox mBox3;
    private ScoreBox mBox4;
    private ScoreBox mBox5;
    private ScoreBox mBox6;
    private ScoreBox mBox7;
    private ScoreBox mBox8;

    public ScoreRow(String name) {
        mName = name;
    }
    public void init1(ScoreBox scoreBox) {
        mBox1 = scoreBox;
    }
    public void init2(ScoreBox scoreBox) {
        mBox2 = scoreBox;
    }
    public void init3(ScoreBox scoreBox) {
        mBox3 = scoreBox;
    }
    public void init4(ScoreBox scoreBox) {
        mBox4 = scoreBox;
    }
    public void init5(ScoreBox scoreBox) {
        mBox5 = scoreBox;
    }
    public void init6(ScoreBox scoreBox) {
        mBox6 = scoreBox;
    }
    public void init7(ScoreBox scoreBox) {
        mBox7 = scoreBox;
    }
    public void init8(ScoreBox scoreBox) {
        mBox8 = scoreBox;
    }

    public ScoreRow() {
        this("Default");
    }

    public String getName() {
        return mName;
    }

    public ScoreBox getBox1() {
        return mBox1;
    }

    public ScoreBox getBox2() {
        return mBox2;
    }

    public ScoreBox getBox3() {
        return mBox3;
    }
    public ScoreBox getBox4() {
        return mBox4;
    }
    public ScoreBox getBox5() {
        return mBox5;
    }
    public ScoreBox getBox6() {
        return mBox6;
    }
    public ScoreBox getBox7() {
        return mBox7;
    }
    public ScoreBox getBox8() {
        return mBox8;
    }

    public void setScore(int opponent, ScoreBox scoreBox) {
        switch (opponent) {
            case 7:
                setScore8(scoreBox);
                break;
            case 6:
                setScore7(scoreBox);
                break;
            case 5:
                setScore6(scoreBox);
                break;
            case 4:
                setScore5(scoreBox);
                break;
            case 3:
                setScore4(scoreBox);
                break;
            case 2:
                setScore3(scoreBox);
                break;
            case 1:
                setScore2(scoreBox);
                break;
            case 0:
                setScore1(scoreBox);
                break;
        }
    }

    public void setScore1(ScoreBox scoreBox) {
        mBox1 = scoreBox;
    }
    public void setScore2(ScoreBox scoreBox) {
        mBox2 = scoreBox;
    }
    public void setScore3(ScoreBox scoreBox) {
        mBox3 = scoreBox;
    }
    public void setScore4(ScoreBox scoreBox) {
        mBox4 = scoreBox;
    }
    public void setScore5(ScoreBox scoreBox) {
        mBox5 = scoreBox;
    }
    public void setScore6(ScoreBox scoreBox) {
        mBox6 = scoreBox;
    }
    public void setScore7(ScoreBox scoreBox) {
        mBox7 = scoreBox;
    }
    public void setScore8(ScoreBox scoreBox) {
        mBox8 = scoreBox;
    }


}