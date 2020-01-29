package com.example.android.directorscut;

import android.widget.TextView;

public class ScoreRow {
    private String mName;
    private int mBox1;
    private int mBox2;
    private int mBox3;
    private int mBox4;
    private int mBox5;
    private int mBox6;
    private int mBox7;
    private int mBox8;

    public ScoreRow(String name) {
        mName = name;
        mBox1 = 0;
        mBox2 = 0;
    }

    public void init3() {
        mBox3 = 0;
    }
    public void init4() {
        mBox4 = 0;
        init3();
    }
    public void init5() {
        mBox5 = 0;
        init4();
    }
    public void init6() {
        mBox6 = 0;
        init5();
    }
    public void init7() {
        mBox7 = 0;
        init6();
    }
    public void init8() {
        mBox8 = 0;
        init7();
    }

    public ScoreRow() {
        this("Default");
    }

    public String getName() {
        return mName;
    }

    public int getBox1() {
        return mBox1;
    }

    public int getBox2() {
        return mBox2;
    }

    public int getBox3() {
        return mBox3;
    }
    public int getBox4() {
        return mBox4;
    }
    public int getBox5() {
        return mBox5;
    }
    public int getBox6() {
        return mBox6;
    }
    public int getBox7() {
        return mBox7;
    }
    public int getBox8() {
        return mBox8;
    }


}