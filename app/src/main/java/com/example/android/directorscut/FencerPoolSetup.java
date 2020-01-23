package com.example.android.directorscut;

// OBSOLETE

public class FencerPoolSetup {
    private static final String STR_UNAT = "Unattached";

    private int mLocalIndex;
    private String mName;
    private FencerRating mRating;
    private String mClub;

    public FencerPoolSetup(int localIndex, String name, FencerRating rating, String club) {
        this.mLocalIndex = localIndex;
        this.mName = name;
        this.mRating = rating;
        this.mClub = club;
    }

    //Getters

    public int getLocalIndex() {
        return mLocalIndex;
    }

    public String getName() {
        return mName;
    }

    public FencerRating getRating() {
        return mRating;
    }

    public String getRatingAsString() {
        return mRating.toString();
    }

    public String getClub() {
        return mClub;
    }

    //Setters

    public void setName(String name) {
        this.mName = name;
    }

    public void setRating(FencerRating.Rating rating, int year) {
        this.mRating = new FencerRating(rating, year);
    }

    public void setClub(String club) {
        if (club.length() <= 0) {
            club = STR_UNAT;
        }
        this.mClub = club;
    }


}
