package com.example.android.directorscut;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class Fencer implements Comparable<Fencer>, Parcelable {
    private static int numFencers;
    private static int genericFencers = 1;
    private String lastName;
    private String club;
    private int rating = 0;
    private int ratingYear = 0;
    private int localIndex;
    private int vic;
    private int ts;
    private int tr;
    private int ind;
    private int place;
    private boolean leftHanded = false;

    public Fencer(String name, int rating, int ratingYear) {
        this(name);
        this.setRating(rating);
        this.setRatingYear(ratingYear);
    }

    public Fencer(String name) {
        this.setLocalIndex(numFencers);
        numFencers++;
        this.setLastName(name);
        this.setClub("Unattached");
    }

    public Fencer() {
        this("Fencer-" + genericFencers);
        genericFencers++;
    }

    protected Fencer(Parcel in) {
        lastName = in.readString();
        club = in.readString();
        rating = in.readInt();
        ratingYear = in.readInt();
        localIndex = in.readInt();
        vic = in.readInt();
        ts = in.readInt();
        tr = in.readInt();
        ind = in.readInt();
        place = in.readInt();
        leftHanded = in.readByte() != 0;
    }

    public static final Creator<Fencer> CREATOR = new Creator<Fencer>() {
        @Override
        public Fencer createFromParcel(Parcel in) {
            return new Fencer(in);
        }

        @Override
        public Fencer[] newArray(int size) {
            return new Fencer[size];
        }
    };

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

    public int getRating() {
        return rating;
    }

    public int getRatingYear() {
        return ratingYear;
    }

    public String getRatingString() {
        String ratingStr = "";
        String letter = "U";
        switch (this.getRating()) {
            case 1:
                letter = "E";
                break;
            case 2:
                letter = "D";
                break;
            case 3:
                letter = "C";
                break;
            case 4:
                letter = "B";
                break;
            case 5:
                letter = "A";
                break;
            default:
                letter = "U";
        }
        String yearString = "";
        int year = this.getRatingYear();
        if (year > 0) {
            yearString = " " + year;
        }
        return ratingStr + letter + yearString;
    }

    public String getClub() {
        if (this.club != null) {
            return this.club;
        }
        return "";
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

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRatingYear(int year) {
        this.ratingYear = year;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void updateInd() {
        this.ind = ts - tr;
    }

    //Resetters
    public void reset() {
        resetVic();
        resetTR();
        resetTS();
        updateInd();
        setPlace(0);
    }

    public void resetTS() {
        ts = 0;
    }

    public void resetTR() {
        tr = 0;
    }

    public void resetVic() {
        vic = 0;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lastName);
        parcel.writeString(club);
        parcel.writeInt(rating);
        parcel.writeInt(ratingYear);
        parcel.writeInt(localIndex);
        parcel.writeInt(vic);
        parcel.writeInt(ts);
        parcel.writeInt(tr);
        parcel.writeInt(ind);
        parcel.writeInt(place);
        parcel.writeByte((byte) (leftHanded ? 1 : 0));
    }
}
