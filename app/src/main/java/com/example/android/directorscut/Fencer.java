package com.example.android.directorscut;

public class Fencer {
    static int numFencers;
    static int genericFencers = 1;
    private String lastName;
    private int vic;
    private int ts;
    private int tr;
    private int ind;
    private int place;
    private boolean leftHanded = false;

    public Fencer(String Name) {
        numFencers++;
        this.lastName = Name;
    }

    public Fencer() {
        this("JonDow_" + genericFencers);
        genericFencers++;
    }

    // Getters
    public String getLastName() {
        return lastName;
    }

    public int getNumFencers() {
        return numFencers;
    }

    public int getVic() {
        return vic;
    }

    public int getTS() {
        return ts;
    }

    public int getTR() {
        return tr;
    }

    public int getInd() {
        return ind;
    }

    public int getPlace() {
        return place;
    }

    public boolean isLeftHanded() {
        return leftHanded;
    }

    // Setters
    public void setPlace(int place) {
        this.place = place;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLeftHanded(boolean leftHanded) {
        this.leftHanded = leftHanded;
    }

    public void updateInd() {
        this.ind = ts - tr;
    }

    // Math
    public void addVictory(int numVics) {
        this.vic += numVics;
    }

    public void addVictory() {
        this.addVictory(1);
    }

    public void addTS(int myScore) {
        this.ts += myScore;
        updateInd();
    }

    public void addTR(int opScore) {
        this.tr += opScore;
        updateInd();
    }



}
