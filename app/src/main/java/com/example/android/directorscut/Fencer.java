package com.example.android.directorscut;

import java.util.Comparator;

public class Fencer implements Comparable< Fencer > {
    private static int numFencers;
    private static int genericFencers = 1;
    private String lastName;
    private int localIndex;
    private int vic;
    private int ts;
    private int tr;
    private int ind;
    private int place;
    private boolean leftHanded = false;

    public Fencer(String name) {
        this.setLocalIndex(numFencers);
        numFencers++;
        this.lastName = name;
    }

    public Fencer() {
        this("JonDow_" + genericFencers);
        genericFencers++;
    }

    @Override
    public String toString() {
        return "Fencer" + this.getLocalIndex() +
                "[Name: "  + this.getLastName() + " " +
                this.getPlace() + "(" + this.getVic() + "," + this.getInd() + ")]";
    }

    // Getters
    public String getLastName() {
        return lastName;
    }

    public int getNumFencers() {
        return numFencers;
    }

    public Integer getVic() {
        return vic;
    }

    public int getLocalIndex() {
        return localIndex;
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
    public void setLocalIndex(int index){
        this.localIndex = index;
    }

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

    // Comparators
    @Override
    public int compareTo(Fencer fencer){
        // Sort by original order
        return localIndex - fencer.getLocalIndex();
    }

    public static Comparator<Fencer> fencerPlacementComparator = new Comparator<Fencer>() {
        // Sort by Place, then by Indicator
        @Override
        public int compare(Fencer me, Fencer fencer) {
            int comp = fencer.getVic() - me.vic;
            if (comp != 0) {
                return comp;
            }
            return fencer.getInd() - me.ind;
        }
    };

    public static Comparator<Fencer> fencerIndexComparator = new Comparator<Fencer>() {
        @Override
        public int compare(Fencer f0, Fencer f1) {
            int index0 = f0.getLocalIndex();
            int index1 = f1.getLocalIndex();
            return index0 - index1;
        }
    };



}
