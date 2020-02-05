package com.example.android.directorscut;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class ActivityScorekeeper extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener, DialogSKEditTime.DialogEditTimeListener, DialogSKVictor.DialogSKVictorListener {
    // STRING CONSTANTS
    // Bout
    private static final String INTENT_BOUT_INDEX = "bout_index";
    private static final String INTENT_BOUT = "bout_object";
    // Card
    private static final String INTENT_CARD_NAME = "card_name";
    private static final String INTENT_CARD_SIDE = "card_side";
    private static final String INTENT_CARD_TYPE = "card_type";
    private static final int REQCODE_CARD = 1;
    // Dialog Tags
    private static final String DIALOG_EDIT_TIME = "edit_time";
    private static final String DIALOG_VICTOR = "set_victor";

    // CONSTANTS
    // Time
    private final int TIME_INTERVAL_MIL = 1;
    private final long TIME_MIN_THREE = 180000;
    private final long TIME_MIN_ONE = 60000;
    private long TIME_DURATION = TIME_MIN_THREE;
    // Score booleans
    private boolean scoreFOTR;
    private boolean scoreFOTL;

    private String cardName = "";
    private String cardSide = "";

    private Bout bout;
    private int activeBout;

    private TextView countdownText;
    private TextView actionTimerText;
    private TextView actionIndL;
    private TextView actionIndR;

    private Button boutReset;
    private Button boutMinuteBreak;
    private Button boutSubmit;
    private Button editTimer;

    private Button nameFOTL;
    private Button nameFOTR;

    Button plusLeft;
    Button plusRight;
    Button minusLeft;
    Button minusRight;

    private Button doubleTouch;
    private Button countdownButton;

    private TextView scoreTextViewL;
    private TextView scoreTextViewR;

    private CountDownTimer mainCDT;
    private int timeUpdateInterval = TIME_INTERVAL_MIL;
    private long timeLeftInMilliseconds = TIME_DURATION; //3 mins
    private boolean timerRunning;
    private boolean timerDone = false;
    private boolean timerMinute = false;
    // SubTimer
    private CountDownTimer actionCDT;
    private ActTimer actionTimer = new ActTimer(TIME_MIN_ONE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getSupportActionBar()!= null) getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorekeeper);
        String FOTR = "RIGHT";
        String FOTL = "LEFT";
        scoreFOTL = false;
        scoreFOTR = false;
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_BOUT)) {
            bout = intent.getParcelableExtra(INTENT_BOUT);
            System.out.println(bout);
            FOTR = bout.getMyName();
            FOTL = bout.getOpName();
        } else {
            bout = new Bout(0, 0);
        }
        if (intent.hasExtra(INTENT_BOUT_INDEX)) {
            activeBout = intent.getIntExtra(INTENT_BOUT_INDEX, 0);
        }
        setupViews();
        nameFOTL.setText(FOTL);
        nameFOTR.setText(FOTR);
    }

    @Override
    public void applyEditTime(String minute, String second) {
        int min = Integer.valueOf(minute);
        int sec = Integer.valueOf(second);

        long minMills = min * 60_000;
        long secMills = sec * 1000;

        timeLeftInMilliseconds = minMills + secMills;
        updateTimerView();
    }

    @Override
    public void onClick(View view) {
        // Get score from TextView
        switch(view.getId()) {
            case R.id.clock_timer:
            case R.id.time_toggle:
                // Main Timer
                if (timeLeftInMilliseconds > 0) {
                    if (timerRunning) {
                        stopTimer();
                    } else {
                        startTimer();
                    }
                }
                // Sub Timer
                if (actionTimer.getTimeLeft() > 0) {
                    if (actionTimer.getState() == ActTimer.TimeState.ACTIVE) {
                        // stop subtimer
                        stopActTimer();
                    } else if ((actionTimer.getState() == ActTimer.TimeState.PAUSED ||
                            actionTimer.getState() == ActTimer.TimeState.ENTER) && !timerMinute){
                        // start subtimer
                        scoreFOTR = false;
                        scoreFOTL = false;
                        startActTimer();
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
            boolean checkZeros = false;
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
                case R.id.bt_edit_time:
                    openTimeDialog();
                    break;
                // Scores
                case R.id.double_touch:
                    bout.addDouble();
                    if (!scoreFOTL && !scoreFOTR){
                        scoreFOTR = true;
                        scoreFOTL = true;
                    }
                    resetActTimer();
                    break;
                case R.id.btn_name_FOTL:
                case R.id.plus_FOTL:
                    if (!scoreFOTR) {
                        scoreFOTL = true;
                        resetActTimer();
                    }
                    bout.addOpScore(bout.isSwap());
                    break;
                case R.id.btn_name_FOTR:
                case R.id.plus_FOTR:
                    if (!scoreFOTL) {
                        scoreFOTR = true;
                        resetActTimer();
                    }
                    bout.addMyScore(bout.isSwap());
                    break;
                case R.id.minus_FOTL:
                    if (bout.isSwap()) {
                        if (bout.getMyScore() > 0) {
                            bout.subMyScore();
                        }
                    } else {
                        if (bout.getOpScore() > 0) {
                            bout.subOpScore();
                        }
                    }
                    if (scoreFOTL && !scoreFOTR) {
                        prevActTimer();
                        scoreFOTL = false;
                    }
                    break;
                case R.id.minus_FOTR:
                    if (bout.isSwap()) {
                        if (bout.getOpScore() > 0) {
                            bout.subOpScore();
                        }
                    } else {
                        if (bout.getMyScore() > 0) {
                            bout.subMyScore();
                        }
                    }

                    if (!scoreFOTL && scoreFOTR) {
                        prevActTimer();
                        scoreFOTR = false;
                    }
                    break;
            }
        }
        // send to TextView
        scoreTextViewL.setText(String.format(Locale.US, "%d", bout.getOpScore()));
        scoreTextViewR.setText(String.format(Locale.US, "%d", bout.getMyScore()));
        updateActTimerView();
    }

    @Override
    public boolean onLongClick(View view){
        // Get score from TextView
        switch(view.getId()) {
            case R.id.time_toggle:
                if (!timerDone) break;
            case R.id.bout_reset:
                if (!timerRunning) {
                    bout.setOpScore(0);
                    bout.setMyScore(0);
                    resetBout();
                    makeToast("Resetting Bout");
                } else {
                    makeToast("Cannot Reset Active Bout!");
                }
                break;
            case R.id.double_touch:
                if (scoreFOTL && scoreFOTR) {
                    makeToast("Removed Double!");
                    scoreFOTR = false;
                    scoreFOTL = false;
                    prevActTimer();
                }
                bout.subDouble();
                break;
            case R.id.bout_submit:
                break;
        }
        // send to TextView
        scoreTextViewL.setText(String.format(Locale.US, "%d", bout.getOpScore()));
        scoreTextViewR.setText(String.format(Locale.US, "%d", bout.getMyScore()));
        return true;
    }


    // ACTION TIMER

    public void startActTimer() {
        actionCDT = new CountDownTimer(actionTimer.getTimeLeft(), timeUpdateInterval) {
            @Override
            public void onTick(long l) {
                actionTimer.setTimeLeft(l);
                actionTimer.stateActive();
                updateActTimerView();
            }

            @Override
            public void onFinish() {
                actionTimer.stateEnd();
                stopTimer();
                updateActTimerView();
            }
        }.start();
    }

    private void resetActTimer() {
        actionTimer.resetTimeLeft();
        updateActTimerView();
    }

    private void prevActTimer() {
        actionTimer.prevTimeLeft();
        updateActTimerView();
    }

    private void stopActTimer() {
        actionTimer.statePaused();
        actionCDT.cancel();
        updateActTimerView();
    }

    private void checkActState(){
        switch (actionTimer.getState()){
            case END:
                // flag and reset
                break;
            case PAUSED:
                break;
        }
    }

    private void updateActTimerView() {
        actionTimerText.setText(timeToText(actionTimer.getTimeLeft(), true));
        int active = getResources().getColor(R.color.cardview_light_background);
        int inactive = getResources().getColor(R.color.cardview_dark_background);
        int colorL = scoreFOTL ? active : inactive;
        int colorR = scoreFOTR ? active : inactive;
        int editVis = timerRunning ? View.INVISIBLE : View.VISIBLE;
        editTimer.setVisibility(editVis);
        actionIndL.setBackgroundColor(colorL);
        actionIndR.setBackgroundColor(colorR);
    }

    // MAIN TIMER

    public void startTimer() {
        mainCDT = new CountDownTimer(timeLeftInMilliseconds, timeUpdateInterval) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimerView();
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
                updateTimerView();
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
        mainCDT.cancel();
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
            updateTimerView();
        }
    }

    public void updateTimerView() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        if (minutes < 1) {
            countdownText.setTextSize(120);
        }
        countdownText.setText(timeToText(timeLeftInMilliseconds, true));
    }

    private String timeToText(long millisec, boolean plusMils){
        String timeLeftText;
        int minutes = (int) millisec / 60000;
        int seconds = (int) (millisec % 60000) / 1000;
        int milsecs = (int) (millisec % 1000) / 10;
        timeLeftText = "";
        timeLeftText += minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += '0';
        timeLeftText += seconds;
        if (minutes <= 0 && plusMils) {
            timeLeftText += ".";
            if (milsecs < 10) timeLeftText += '0';
            timeLeftText += milsecs;
        }
        return timeLeftText;
    }

    public void makeToast(String message) {
        Toast aToast =
                Toast.makeText(this, message, Toast.LENGTH_SHORT);
        aToast.show();
    }

    public void resetBout() {
        countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnInit));
        scoreFOTR = false;
        scoreFOTL = false;
        breakExit();
        resetTimer();
        resetActTimer();
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
        if (!bout.getMyScore().equals(bout.getOpScore())) {
            bout.endBout();
            terminateActivity();
        } else {
            openDialogVictor();
        }
    }

    public void terminateActivity() {
        Intent resultSK = new Intent(this, ActivityPool.class);
        System.out.println(bout);
        resultSK.putExtra(INTENT_BOUT, bout);
        resultSK.putExtra(INTENT_BOUT_INDEX, activeBout);
        setResult(RESULT_OK, resultSK);
        finish();
    }

    public void openDialogVictor() {
        DialogSKVictor dialogVictor = new DialogSKVictor();
        dialogVictor.show(getSupportFragmentManager(), DIALOG_VICTOR);
    }

    public void cardPopup(View v) {
        PopupMenu cardMenu = new PopupMenu(this, v);
        switch (v.getId()) {
            case R.id.radial_card_left:
                cardName = bout.getOpName();
                cardSide = "Left";
                break;
            case R.id.radial_card_right:
                cardName = bout.getMyName();
                cardSide = "Right";
                break;
        }
        cardMenu.setOnMenuItemClickListener(this);
        cardMenu.inflate(R.menu.card_popup);
        cardMenu.show();
    }

    public void createCard(int color) {
        Intent cardIntent = new Intent(ActivityScorekeeper.this, ActivityCard.class);
        cardIntent.putExtra(INTENT_CARD_TYPE, color);
        cardIntent.putExtra(INTENT_CARD_NAME, cardName);
        cardIntent.putExtra(INTENT_CARD_SIDE, cardSide);
        startActivityForResult(cardIntent, REQCODE_CARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_card_yellow:
                createCard(0);
                return true;
            case R.id.item_card_red:
                createCard(1);
                return true;
            case R.id.item_card_black:
                createCard(2);
                return true;
        }
        return false;
    }

    private void setupViews() {
//        bout.setSwap(true);
        if (bout.isSwap()) {
            scoreTextViewR = (TextView) findViewById(R.id.score_FOTL);
            plusRight = (Button) findViewById(R.id.plus_FOTL);
            nameFOTR = (Button) findViewById(R.id.btn_name_FOTL);
            minusRight = (Button) findViewById(R.id.minus_FOTL);

            scoreTextViewL = (TextView) findViewById(R.id.score_FOTR);
            plusLeft = (Button) findViewById(R.id.plus_FOTR);
            nameFOTL = (Button) findViewById(R.id.btn_name_FOTR);
            minusLeft = (Button) findViewById(R.id.minus_FOTR);
        } else {
            scoreTextViewL = (TextView) findViewById(R.id.score_FOTL);
            plusLeft = (Button) findViewById(R.id.plus_FOTL);
            nameFOTL = (Button) findViewById(R.id.btn_name_FOTL);
            minusLeft = (Button) findViewById(R.id.minus_FOTL);

            scoreTextViewR = (TextView) findViewById(R.id.score_FOTR);
            plusRight = (Button) findViewById(R.id.plus_FOTR);
            nameFOTR = (Button) findViewById(R.id.btn_name_FOTR);
            minusRight = (Button) findViewById(R.id.minus_FOTR);
        }

        boutReset = (Button) findViewById(R.id.bout_reset);
        boutMinuteBreak = (Button) findViewById(R.id.bout_minuteBreak);
        boutSubmit = (Button) findViewById(R.id.bout_submit);
        editTimer = (Button) findViewById(R.id.bt_edit_time);

        countdownText = (TextView) findViewById(R.id.clock_timer);
        actionTimerText = (TextView) findViewById(R.id.tv_subtimer);

        countdownButton = (Button) findViewById(R.id.time_toggle);
        actionIndL = (TextView) findViewById(R.id.tv_subtimer_left_ind);
        actionIndR = (TextView) findViewById(R.id.tv_subtimer_right_ind);
        doubleTouch = (Button) findViewById(R.id.double_touch);


        // BUTTONS
        // Bout Status
        boutReset.setOnClickListener(this);
        boutReset.setOnLongClickListener(this);
        boutMinuteBreak.setOnClickListener(this);
        boutSubmit.setOnClickListener(this);
        // Countdown
        editTimer.setOnClickListener(this);
        countdownText.setOnClickListener(this);
        countdownButton.setOnClickListener(this);
        countdownButton.setOnLongClickListener(this);
        // Score
        nameFOTL.setOnClickListener(this);
        nameFOTR.setOnClickListener(this);

        plusLeft.setOnClickListener(this);
        plusRight.setOnClickListener(this);
        doubleTouch.setOnClickListener(this);
        doubleTouch.setOnLongClickListener(this);
        minusLeft.setOnClickListener(this);
        minusRight.setOnClickListener(this);
        scoreTextViewL.setText(bout.getOpScore().toString());
        scoreTextViewR.setText(bout.getMyScore().toString());
    }

    public void openTimeDialog() {
        DialogSKEditTime dialogSKEditTime = new DialogSKEditTime();

        Bundle args = new Bundle();
        args.putInt("bMinute", (int) timeLeftInMilliseconds / 60_000);
        args.putInt("bSecond", (int) timeLeftInMilliseconds / 1000);

        dialogSKEditTime.show(getSupportFragmentManager(), DIALOG_EDIT_TIME);
    }

    @Override
    public void onRightClicked() {
        bout.endBout(true);
        terminateActivity();
    }

    @Override
    public void onLeftClicked() {
        bout.endBout(false);
        terminateActivity();
    }
}
