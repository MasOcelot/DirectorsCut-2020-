package com.example.android.directorscut;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
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

    enum BoutState {
        START, FIRST, HALF, SECOND, END
    }

    private BoutState mBoutState;

    Vibrator vibrator;

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
    private String cardSideText = "";
    private int cardSide = -1;
    private String FOTR = "RIGHT";
    private String FOTL = "LEFT";

    private Bout bout;
    private int activeBout;

    private int myYellow = 0;
    private int myRed = 0;
    private int myBlack = 0;

    private int opYellow = 0;
    private int opRed = 0;
    private int opBlack = 0;

    private ConstraintLayout statusMain;
    private TextView statusFirst;
    private TextView statusHalf;
    private TextView statusSecond;

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

    private Button plusLeft;
    private Button plusRight;
    private Button minusLeft;
    private Button minusRight;

    private Button doubleTouch;
    private Button countdownButton;

    private TextView scoreTextViewL;
    private TextView scoreTextViewR;

    private TextView cardCountMY;
    private TextView cardCountMR;
    private TextView cardCountMB;

    private TextView cardCountOY;
    private TextView cardCountOR;
    private TextView cardCountOB;

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
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mBoutState = BoutState.START;
        parseIntents();
        setupViews();
        updateStatusBar();
        scoreFOTL = false;
        scoreFOTR = false;
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
    public void applyActEditTime(String minute, String second) {
        int min = Integer.valueOf(minute);
        int sec = Integer.valueOf(second);

        long minMills = min * 60_000;
        long secMills = sec * 1000;

        actionTimer.setTimeLeft(minMills + secMills);
        updateActTimerView();
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
                        if (mBoutState == BoutState.START) mBoutState = BoutState.FIRST;
                        if (mBoutState == BoutState.HALF && !timerMinute) mBoutState = BoutState.SECOND;
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
                        startTimer();
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
        updateStatusBar();
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
        updateScores();
        return true;
    }

    private void parseIntents() {
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
    }

    private void updateStatusBar() {
        int main;
        Drawable first;
        Drawable half;
        Drawable second;
        switch (mBoutState) {
            case FIRST:
                main = getResources().getColor(R.color.grid_ignore);
                first = getResources().getDrawable(R.drawable.bg_status_active);
                half = getResources().getDrawable(R.drawable.bg_status_innate);
                second = getResources().getDrawable(R.drawable.bg_status_innate);
                break;
            case HALF:
                main = getResources().getColor(R.color.grid_ignore);
                first = getResources().getDrawable(R.drawable.bg_status_passed);
                half = getResources().getDrawable(R.drawable.bg_status_active);
                second = getResources().getDrawable(R.drawable.bg_status_innate);
                break;
            case SECOND:
                main = getResources().getColor(R.color.grid_ignore);
                first = getResources().getDrawable(R.drawable.bg_status_passed);
                half = getResources().getDrawable(R.drawable.bg_status_passed);
                second = getResources().getDrawable(R.drawable.bg_status_active);
                break;
            case END:
                main = getResources().getColor(R.color.card_red);
                first = getResources().getDrawable(R.drawable.bg_status_passed);
                half = getResources().getDrawable(R.drawable.bg_status_passed);
                second = getResources().getDrawable(R.drawable.bg_status_passed);
                break;
            case START:
            default:
                main = getResources().getColor(R.color.grid_ignore);
                first = getResources().getDrawable(R.drawable.bg_status_innate);
                half = getResources().getDrawable(R.drawable.bg_status_innate);
                second = getResources().getDrawable(R.drawable.bg_status_innate);
                break;
        }
        statusMain.setBackgroundColor(main);
        statusFirst.setBackgroundDrawable(first);
        statusHalf.setBackgroundDrawable(half);
        statusSecond.setBackgroundDrawable(second);
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
                evokeVibration(200);
                actionTimer.stateEnd();
                stopTimer();
                updateActTimerView();
            }
        }.start();
    }

    private void evokeVibration(int mills) {
        if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(mills, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(mills);
        }
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
                evokeVibration(800);
                stopActTimer();

                timerRunning = false;
                timerDone = true;
                if (!timerMinute) {
                    timeLeftInMilliseconds = 0;
                    makeToast("Time's up!");
                } else {
                    timeLeftInMilliseconds = TIME_DURATION;
                    breakExit();
                }
                if (mBoutState == BoutState.FIRST) {
                    breakEnter();
                }
                if (mBoutState == BoutState.SECOND) {
//                    mBoutState = BoutState.END;
                    countdownText.setBackgroundColor(getResources().getColor(R.color.timeFinal));
                    countdownButton.setText(R.string.timeBtnFinal);
                    countdownButton.setTextSize(30);
                    countdownButton.setTextColor(getResources().getColor(R.color.timeTextFin));
                    countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnFinal));
                }
                updateTimerView();
                updateStatusBar();
            }
        }.start();
        countdownButton.setText(R.string.timeBtnStop);
        countdownButton.setTextColor(getResources().getColor(R.color.timeTextRes));
        int cdbColor;
        int cdtColor;
        if (timerMinute){
            cdbColor = getResources().getColor(R.color.timeBtnResBreak);
            cdtColor = getResources().getColor(R.color.timeBtnResBreak);
        } else {
            cdbColor = getResources().getColor(R.color.timeBtnResumed);
            cdtColor = getResources().getColor(R.color.timeResumed);
        }
        countdownButton.setBackgroundColor(cdbColor);
        countdownText.setBackgroundColor(cdtColor);
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
            if(mBoutState == BoutState.END) mBoutState = BoutState.START;
            if(mBoutState == BoutState.HALF) mBoutState = BoutState.SECOND;
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
        mBoutState = BoutState.START;
        clearCards();
        updateCardInds();
        breakExit();
        resetTimer();
        resetActTimer();
        updateStatusBar();
    }

    private void clearCards() {
        myYellow = 0;
        opYellow = 0;
        myRed = 0;
        opRed = 0;
        myBlack = 0;
        opBlack = 0;
    }

    public void breakEnter() {
        mBoutState = BoutState.HALF;
        timerMinute = true;
        timeLeftInMilliseconds = TIME_MIN_ONE;
        boutMinuteBreak.setText(R.string.btnMinuteActive);
        makeToast("One Minute Break");
    }

    public void breakExit() {
        timerMinute = false;
        boutMinuteBreak.setText(R.string.btnMinuteInactive);
        countdownButton.setBackgroundColor(getResources().getColor(R.color.timeBtnInit));
        resetTimer();
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
                cardSideText = "Left";
                cardSide = 0;
                break;
            case R.id.radial_card_right:
                cardName = bout.getMyName();
                cardSideText = "Right";
                cardSide = 1;
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
        cardIntent.putExtra(INTENT_CARD_SIDE, cardSideText);
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
                addCard(0, cardSide);
                return true;
            case R.id.item_card_red:
                addCard(1, cardSide);
                return true;
            case R.id.item_card_black:
                addCard(2, cardSide);
                return true;
        }
        return false;
    }

    private void addCard(int color, int side) {
        switch (side) {
            case 0:
                switch (color) {
                    case 0:
                        if (opYellow>0) {
                            opRed++;
                            bout.addMyScore();
                            createCard(1);
                        } else {
                            opYellow++;
                            createCard(0);
                        }
                        break;
                    case 1:
                        opRed++;
                        bout.addMyScore();
                        createCard(1);
                        break;
                    case 2:
                        opBlack++;
                        createCard(2);
                        bout.endBout(true);
                        terminateActivity();
                        break;
                }
                break;
            case 1:
                switch (color) {
                    case 0:
                        if (myYellow>0) {
                            myRed++;
                            bout.addOpScore();
                            createCard(1);
                        } else {
                            myYellow++;
                            createCard(0);
                        }
                        break;
                    case 1:
                        myRed++;
                        bout.addOpScore();
                        createCard(1);
                        break;
                    case 2:
                        myBlack++;
                        createCard(2);
                        bout.endBout(false);
                        terminateActivity();
                        break;
                }
                break;
        }
        updateScores();
        updateCardInds();
    }


    private void setupViews() {
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

        statusMain = findViewById(R.id.LL_bout_status);
        statusFirst = findViewById(R.id.tv_mid_first);
        statusHalf = findViewById(R.id.tv_mid_break);
        statusSecond = findViewById(R.id.tv_mid_second);

        countdownText = (TextView) findViewById(R.id.clock_timer);
        actionTimerText = (TextView) findViewById(R.id.tv_subtimer);

        countdownButton = (Button) findViewById(R.id.time_toggle);
        actionIndL = (TextView) findViewById(R.id.tv_subtimer_left_ind);
        actionIndR = (TextView) findViewById(R.id.tv_subtimer_right_ind);
        doubleTouch = (Button) findViewById(R.id.double_touch);

        cardCountMY = findViewById(R.id.tv_my_yellow);
        cardCountMR = findViewById(R.id.tv_my_red);
        cardCountMB = findViewById(R.id.tv_my_black);
        cardCountOY = findViewById(R.id.tv_op_yellow);
        cardCountOR = findViewById(R.id.tv_op_red);
        cardCountOB = findViewById(R.id.tv_op_black);


        // BUTTONS
        // Bout Control
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

    private void updateCardInds() {
        if (myRed > 0) {
            cardCountMR.setBackgroundColor(getResources().getColor(R.color.card_red));
        } else {
            cardCountMR.setBackgroundColor(getResources().getColor(R.color.grid_ignore));
        }
        if (myYellow > 0) {
            cardCountMY.setBackgroundColor(getResources().getColor(R.color.card_yellow));
        } else {
            cardCountMY.setBackgroundColor(getResources().getColor(R.color.grid_ignore));
        }
        if (opRed > 0) {
            cardCountOR.setBackgroundColor(getResources().getColor(R.color.card_red));
        } else {
            cardCountOR.setBackgroundColor(getResources().getColor(R.color.grid_ignore));
        }
        if (opYellow > 0) {
            cardCountOY.setBackgroundColor(getResources().getColor(R.color.card_yellow));
        } else {
            cardCountOY.setBackgroundColor(getResources().getColor(R.color.grid_ignore));
        }
        cardCountMR.setText(String.valueOf(myRed));
        cardCountMY.setText(String.valueOf(myYellow));
        cardCountOR.setText(String.valueOf(opRed));
        cardCountOY.setText(String.valueOf(opYellow));
    }

    private void updateScores() {
        scoreTextViewL.setText(String.format(Locale.US, "%d", bout.getOpScore()));
        scoreTextViewR.setText(String.format(Locale.US, "%d", bout.getMyScore()));
    }
}
