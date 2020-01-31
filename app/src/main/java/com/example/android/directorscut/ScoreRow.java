package com.example.android.directorscut;

import android.widget.TextView;

public class ScoreRow {
    private String mName;
    private ScoreBox mBox0;
    private ScoreBox mBox1;
    private ScoreBox mBox2;
    private ScoreBox mBox3;
    private ScoreBox mBox4;
    private ScoreBox mBox5;
    private ScoreBox mBox6;
    private ScoreBox mBox7;

    public ScoreRow(String name) {
        mName = name;
    }
    public ScoreRow() {
        this("Default");
    }

    public String getName() {
        return mName;
    }

    public void initScoreBoxes(int numBoxes) {
        switch (numBoxes) {
            case 7:
                init7();
            case 6:
                init6();
            case 5:
                init5();
            case 4:
                init4();
            case 3:
                init3();
            case 2:
                init2();
            default:
                init1();
                init0();
        }
    }
    private void init0() {
        mBox0 = new ScoreBox();
    }
    private void init1() {
        mBox1 = new ScoreBox();
    }
    private void init2() {
        mBox2 = new ScoreBox();
    }
    private void init3() {
        mBox3 = new ScoreBox();
    }
    private void init4() {
        mBox4 = new ScoreBox();
    }
    private void init5() {
        mBox5 = new ScoreBox();
    }
    private void init6() {
        mBox6 = new ScoreBox();
    }
    private void init7() {
        mBox7 = new ScoreBox();
    }
    
    public void setScore(int box, int value) {
        try {
            switch (box) {
                case 7:
                    setScore7(value);
                    break;
                case 6:
                    setScore6(value);
                    break;
                case 5:
                    setScore5(value);
                    break;
                case 4:
                    setScore4(value);
                    break;
                case 3:
                    setScore3(value);
                    break;
                case 2:
                    setScore2(value);
                    break;
                case 1:
                    setScore1(value);
                    break;
                case 0:
                    setScore0(value);
                    break;
                default:
                    throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void setScore0(int value) {
        if (mBox0 != null) {
            mBox0.setScore(value);
        }
    }
    private void setScore1(int value) {
        if (mBox1 != null) {
            mBox1.setScore(value);
        }
    }
    private void setScore2(int value) {
        if (mBox2 != null) {
            mBox2.setScore(value);
        }
    }
    private void setScore3(int value) {
        if (mBox3 != null) {
            mBox3.setScore(value);
        }
    }
    private void setScore4(int value) {
        if (mBox4 != null) {
            mBox4.setScore(value);
        }
    }
    private void setScore5(int value) {
        if (mBox5 != null) {
            mBox5.setScore(value);
        }
    }
    private void setScore6(int value) {
        if (mBox6 != null) {
            mBox6.setScore(value);
        }
    }
    private void setScore7(int value) {
        if (mBox7 != null) {
            mBox7.setScore(value);
        }
    }

    public ScoreBox getScoreBox(int box) {
        try {
            switch (box) {
                case 7:
                    return getBox7();
                case 6:
                    return getBox6();
                case 5:
                    return getBox5();
                case 4:
                    return getBox4();
                case 3:
                    return getBox3();
                case 2:
                    return getBox2();
                case 1:
                    return getBox1();
                case 0:
                    return getBox0();
                default:
                    throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
    private ScoreBox getBox0() {
        return mBox0;
    }
    private ScoreBox getBox1() {
        return mBox1;
    }
    private ScoreBox getBox2() {
        return mBox2;
    }
    private ScoreBox getBox3() {
        return mBox3;
    }
    private ScoreBox getBox4() {
        return mBox4;
    }
    private ScoreBox getBox5() {
        return mBox5;
    }
    private ScoreBox getBox6() {
        return mBox6;
    }
    private ScoreBox getBox7() {
        return mBox7;
    }

}