package com.example.android.directorscut;

public class ActTimer {

    enum TimeState {
        ENTER, ACTIVE, PAUSED, END;
    }

    private long mStartTime;
    private long mPrevTime;
    private long mTimeLeft;
    private TimeState mState;

    public ActTimer(long startTime) {
         mStartTime = startTime;
         mTimeLeft = startTime;
         mPrevTime = startTime;
         mState = TimeState.ENTER;
    }

    // SETTERS

    public void setTimeLeft(long timeLeft) {
        mTimeLeft = timeLeft;
    }

    public void stateEnd() {
        mState = TimeState.END;
    }

    public void statePaused() {
        mState = TimeState.PAUSED;
    }

    public void stateActive() {
        mState = TimeState.ACTIVE;
    }

    public void stateEnter() {
        mState = TimeState.ENTER;
    }

    public void storePrevTime() {
        if (this.mTimeLeft != this.getStartTime()) {
            mPrevTime = mTimeLeft;
        }
    }

    public void resetTimeLeft() {
        this.storePrevTime();
        mTimeLeft = mStartTime;
        this.stateEnter();
    }

    public void prevTimeLeft() {
        mTimeLeft = mPrevTime;
        this.statePaused();
    }


    // GETTERS

    public long getTimeLeft() {
        return mTimeLeft;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public TimeState getState() {
        return mState;
    }


}
