package com.example.android.directorscut;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private static final String INTENT_FOTL_SCORE = "score_fotl";
    private TextView countdownText;

    private Button boutReset;
    private Button boutMinuteBreak;
    private Button boutSubmit;

    private Button nameFOTL;
    private Button nameFOTR;

    private Button doubleTouch;
    private Button countdownButton;

    private final int TIME_INTERVAL_MIL = 1;
    private final long TIME_MIN_THREE = 180000;
    private final long TIME_MIN_ONE = 60000;
    private long TIME_DURATION = TIME_MIN_THREE;

    private CountDownTimer countDownTimer;
    private int timeUpdateInterval = TIME_INTERVAL_MIL;
    private long timeLeftInMilliseconds = TIME_DURATION; //3 mins
    private boolean timerRunning;
    private boolean timerDone = false;
    private boolean timerMinute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutReset = (Button) findViewById(R.id.bout_reset);
        boutMinuteBreak = (Button) findViewById(R.id.bout_minuteBreak);
        boutSubmit = (Button) findViewById(R.id.bout_submit);
        countdownText = (TextView) findViewById(R.id.clock_timer);
        countdownButton = (Button) findViewById(R.id.time_toggle);
        doubleTouch = (Button) findViewById(R.id.double_touch);
        nameFOTL = (Button) findViewById(R.id.name_FOTL);
        nameFOTR = (Button) findViewById(R.id.name_FOTR);

        // BUTTONS
        // Bout Status
        boutReset.setOnClickListener(this);
        boutReset.setOnLongClickListener(this);
        boutMinuteBreak.setOnClickListener(this);
        boutSubmit.setOnClickListener(this);
        // Countdown
        countdownText.setOnClickListener(this);
        countdownButton.setOnClickListener(this);
        countdownButton.setOnLongClickListener(this);
        // Score
        nameFOTL.setOnClickListener(this);
        nameFOTR.setOnClickListener(this);
        Button plusLeft = (Button) findViewById(R.id.plus_FOTL);
        plusLeft.setOnClickListener(this);
        Button plusRight = (Button) findViewById(R.id.plus_FOTR);
        plusRight.setOnClickListener(this);
        doubleTouch.setOnClickListener(this);
        doubleTouch.setOnLongClickListener(this);
        Button minusLeft = (Button) findViewById(R.id.minus_FOTL);
        minusLeft.setOnClickListener(this);
        Button minusRight = (Button) findViewById(R.id.minus_FOTR);
        minusRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Get score from TextView
        TextView scoreTextViewL = (TextView) findViewById(R.id.score_FOTL);
        TextView scoreTextViewR = (TextView) findViewById(R.id.score_FOTR);
        // Score TextView value as String
        String scoreStringL = scoreTextViewL.getText().toString();
        String scoreStringR = scoreTextViewR.getText().toString();
        // intConvert and Increment
        Integer scoreL = Integer.parseInt(scoreStringL);
        Integer scoreR = Integer.parseInt(scoreStringR);
        switch(view.getId()) {
            case R.id.clock_timer:
            case R.id.time_toggle:
                if (timeLeftInMilliseconds > 0) {
                    if (timerRunning) {
                        stopTimer();
                    } else {
                        startTimer();
                    }
                }
                break;
            // Bout
            case R.id.bout_reset:
                makeToast("Hold to Reset Bout");
                // TODO: convert to OnTouch (press, instead of release)
                break;
        }
        if (!timerRunning) {
            switch (view.getId()) {
                case R.id.bout_submit:
                    submit();
                    break;
                // Minute
                case R.id.bout_minuteBreak:
                    if (!timerMinute) {
                        breakEnter();
                    } else {
                        breakExit();
                    }
                    break;
                // Scores
                case R.id.double_touch:
                    scoreL++;
                    scoreR++;
                    break;
                case R.id.name_FOTL:
                case R.id.plus_FOTL:
                    scoreL++;
                    break;
                case R.id.name_FOTR:
                case R.id.plus_FOTR:
                    scoreR++;
                    break;
                case R.id.minus_FOTL:
                    if (scoreL > 0) scoreL--;
                    break;
                case R.id.minus_FOTR:
                    if (scoreR > 0) scoreR--;
                    break;
            }
        }
        // send to TextView
        scoreTextViewL.setText(scoreL.toString());
        scoreTextViewR.setText(scoreR.toString());
    }

    @Override
    public boolean onLongClick(View view){
        // Get score from TextView
        TextView scoreTextViewL = (TextView) findViewById(R.id.score_FOTL);
        TextView scoreTextViewR = (TextView) findViewById(R.id.score_FOTR);
        // Score TextView value as String
        String scoreStringL = scoreTextViewL.getText().toString();
        String scoreStringR = scoreTextViewR.getText().toString();
        // intConvert and Increment
        Integer scoreL = Integer.parseInt(scoreStringL);
        Integer scoreR = Integer.parseInt(scoreStringR);
        switch(view.getId()) {
            case R.id.time_toggle:
                if (!timerDone) break;
            case R.id.bout_reset:
                if (!timerRunning) {
                    scoreL = 0;
                    scoreR = 0;
                    resetBout();
                    makeToast("Resetting Bout");
                } else {
                    makeToast("Cannot Reset Active Bout!");
                }
                break;
            case R.id.double_touch:
                if (scoreL > 0) scoreL--;
                if (scoreR > 0) scoreR--;
                break;
            case R.id.bout_submit:
                break;
        }
        // send to TextView
        scoreTextViewL.setText(scoreL.toString());
        scoreTextViewR.setText(scoreR.toString());
        return true;
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, timeUpdateInterval) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                countdownText.setBackgroundColor(getResources().getColor(R.color.timeFinal));
                countdownButton.setText(R.string.timeBtnFinal);
                countdownButton.setTextSize(30);
                countdownButton.setTextColor(getResources().getColor(R.color.timeTextFin));
                countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnFinal));
                timerRunning = false;
                timerDone = true;
                if (!timerMinute) {
                    timeLeftInMilliseconds = 0;
                    makeToast("Time's up!");
                } else {
                    timeLeftInMilliseconds = TIME_DURATION;
                    breakExit();
                }
                updateTimer();
            }
        }.start();
        countdownButton.setText(R.string.timeBtnStop);
        countdownButton.setTextColor(getResources().getColor(R.color.timeTextRes));
        if (timerMinute){
            countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnResBreak));
            countdownText.setBackgroundColor(getResources().getColor(R.color.timeBtnResBreak));
        } else {
            countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnResumed));
            countdownText.setBackgroundColor(getResources().getColor(R.color.timeResumed));
        }

        countdownButton.setTextSize(36);
        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        countdownButton.setText(R.string.timeBtnStart);
        countdownButton.setTextColor(getResources().getColor(R.color.timeTextStop));
        if (timerMinute){
            countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnStopBreak));
            countdownText.setBackgroundColor(getResources().getColor(R.color.timeBtnStopBreak));
        } else {
            countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnStopped));
            countdownText.setBackgroundColor(getResources().getColor(R.color.timeStopped));
        }


        timerRunning = false;
    }

    public void resetTimer() {
        if (!timerRunning) {
            timeLeftInMilliseconds = TIME_DURATION;
            countdownButton.setText(R.string.timerBtnInit);
            countdownButton.setTextColor(getResources().getColor(R.color.timeTextInit));
            countdownButton.setTextSize(32);
            countdownText.setBackgroundColor(getResources().getColor(R.color.timeInitial));
            timerDone = false;
            updateTimer();
        }
    }

    public void updateTimer() {

        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) (timeLeftInMilliseconds % 60000) / 1000;
        int milsecs = (int) (timeLeftInMilliseconds % 1000) / 10;

        String timeLeftText;
        timeLeftText = "";
        timeLeftText += minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += '0';
        timeLeftText += seconds;
        if (minutes <= 0) {
            countdownText.setTextSize(120);
            timeLeftText += ".";
            if (milsecs < 10) timeLeftText += '0';
            timeLeftText += milsecs;
        }
        countdownText.setText(timeLeftText);

    }

    public void makeToast(String message) {
        Toast aToast =
                Toast.makeText(this, message, Toast.LENGTH_SHORT);
        aToast.show();
    }

    public void resetBout() {
        countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnInit));
        breakExit();
        resetTimer();
    }

    public void breakEnter() {
        timerMinute = true;
        timeLeftInMilliseconds = TIME_MIN_ONE;
        boutMinuteBreak.setText(R.string.btnMinuteActive);
        startTimer();
        makeToast("One Minute Break");
    }

    public void breakExit() {
        timerMinute = false;
        boutMinuteBreak.setText(R.string.btnMinuteInactive);
        countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnInit));
        resetTimer();
        makeToast("Break is over!");
    }

    public void submit() {
        Intent poolAct = new Intent(this, Pool.class);
        TextView rightScore = (TextView) findViewById(R.id.score_FOTR);
        String numFencers = rightScore.getText().toString();
        int count = Integer.parseInt(numFencers);
        poolAct.putExtra(INTENT_FOTL_SCORE, count);
        startActivity(poolAct);
    }

}
