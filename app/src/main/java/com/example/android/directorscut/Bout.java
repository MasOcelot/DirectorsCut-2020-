package com.example.android.directorscut;

public class Bout {
    private String myName;
    private String opName;
    private int myNumber;
    private int opNumber;
    private int myScore;
    private int opScore;
    private boolean myVictory = false;
    private boolean complete;

    public Bout(int myNumber, int opNumber) {
        this.myNumber = myNumber;
        this.opNumber = opNumber;
        this.myName = "FOTR";
        this.opName = "FOTL";
        this.myScore = 0;
        this.opScore = 0;
        this.complete = false;
    }

    public Bout(int myNumber, int opNumber, String myName, String opName) {
        this(myNumber, opNumber);
        this.myName = myName;
        this.opName = opName;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
        checkVictory();
    }

    public void setOpScore(int opScore) {
        this.opScore = opScore;
        checkVictory();
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public void checkVictory() {
        if (myScore > opScore) {
            setIsVictory(true);
        } else {
            setIsVictory(false);
        }
    }

    public String getMyName() {
        return myName;
    }

    public String getOpName() {
        return opName;
    }

    public int getMyNumber() {
        return myNumber;
    }

    public int getOpNumber() {
        return opNumber;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getOpScore() {
        return opScore;
    }

    public boolean isMyVictory(){
        return myVictory;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setIsVictory(boolean isVictory){
        this.myVictory = isVictory;
    }

    public void toggleVictory(){
        myVictory = !myVictory;
    }
}
