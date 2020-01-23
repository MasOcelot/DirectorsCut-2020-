package com.example.android.directorscut;

public class FencerRating {

    enum Rating {
        U {
            public String toString() {
                return "U";
            }
        },
        E {
            public String toString() {
                return "E";
            }
        },
        D{
            public String toString() {
                return "D";
            }
        },
        C {
            public String toString() {
                return "C";
            }
        },
        B {
            public String toString() {
                return "B";
            }
        },
        A{
            public String toString() {
                return "A";
            }
        }
    }

    private Rating mRating;
    private int mYear;

    public FencerRating(Rating rating, int year) {
        mRating = rating;
        mYear = year;
    }

    public FencerRating() {
        this(Rating.U, 0);
    }

    public String toString() {
        String letter = mRating.toString();
        String number = mYear > 0 ? String.valueOf(mYear) : "";
        if (number.length() > 0) {
            return letter + " " + number;
        }
        return letter;
    }

    public void setRating(Rating rating) {
        this.mRating = rating;
    }

    public void setYear(int year) {
        this.mYear = year;
    }
}
