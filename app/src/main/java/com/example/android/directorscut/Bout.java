package com.example.android.directorscut;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class Bout implements Parcelable {
    private String myName;
    private String opName;
    private int myNumber;
    private int opNumber;
    private Integer myScore;
    private Integer opScore;
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

    protected Bout(Parcel in) {
        myName = in.readString();
        opName = in.readString();
        myNumber = in.readInt();
        opNumber = in.readInt();
        myScore = in.readInt();
        opScore = in.readInt();
        myVictory = in.readByte() != 0;
        complete = in.readByte() != 0;
    }

    public static final Creator<Bout> CREATOR = new Creator<Bout>() {
        @Override
        public Bout createFromParcel(Parcel in) {
            return new Bout(in);
        }

        @Override
        public Bout[] newArray(int size) {
            return new Bout[size];
        }
    };

    @Override
    public String toString() {
        return "Bout[("+myNumber+"-"+opNumber+") "+myScore+":"+opScore+"]";
    }

    public void addMyScore() {
        myScore++;
    }

    public void addMyScore(int limit) {
        if (this.myScore < limit) {
            addMyScore();
        }
    }

    public void addOpScore() {
        opScore++;
    }

    public void addOpScore(int limit) {
        if (this.opScore < limit) {
            addOpScore();
        }
    }

    public void subMyScore() {
        myScore--;
    }

    public void subOpScore() {
        opScore--;
    }

    public void addDouble() {
        addMyScore();
        addOpScore();
    }

    public void addDouble(int limit) {
        if (this.getMyScore() < limit && this.getOpScore()< limit) {
            addDouble();
        }
    }

    public void subDouble() {
        if (myScore > 0) subMyScore();
        if (opScore > 0) subOpScore();
    }

    public void setMyScore(Integer myScore) {
        this.myScore = myScore;
        checkVictory();
    }

    public void setOpScore(Integer opScore) {
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

    public Integer getMyScore() {
        return myScore;
    }

    public Integer getOpScore() {
        return opScore;
    }

    public boolean isMyVictory(){
        return myVictory;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setComplete() {
        this.setComplete(true);
    }

    public void endBout() {
        if (this.getMyScore() > this.getOpScore()) {
            this.setIsVictory(true);
        } else {
            this.setIsVictory(false);
        }
        this.setComplete();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(myName);
        parcel.writeString(opName);
        parcel.writeInt(myNumber);
        parcel.writeInt(opNumber);
        parcel.writeInt(myScore);
        parcel.writeInt(opScore);
        parcel.writeByte((byte) (myVictory ? 1 : 0));
        parcel.writeByte((byte) (complete ? 1 : 0));
    }
}
