package com.example.android.directorscut;

public class ScoreBox {
    private int score;
    private boolean victory = false;
    private boolean black = false;
    private boolean show = false;
    private boolean tie = false;

    public ScoreBox(int score) {
        this.score = score;
    }

    public ScoreBox() {
        this(0);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isVictory() {
        return victory;
    }

    public boolean isTie() {
        return tie;
    }

    public void setVictory(boolean isVictory) {
        this.victory = isVictory;
    }

    public void setTie(boolean isTie) {
        this.tie = isTie;
    }

    public void toggleVictory() {
        victory = !victory;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean isBlack) {
        this.black = isBlack;
    }

    public void toggleBlack() {
        black = !black;
    }

    public void setShow(boolean show){
        this.show = show;
    }

    public boolean isShow() {
         return show;
    }
}
