package com.example.android.directorscut;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ActivityPool extends AppCompatActivity implements View.OnClickListener {
    // INTENT STRINGS
    private static final String INTENT_BOUT_INDEX = "bout_index";
    private static final String INTENT_NUM_FENCERS = "number_fencers";
    private static final String INTENT_SCORE_LIMIT = "score_limit";
    private static final String INTENT_BOUT = "bout_object";

    // ADAPTERS
    private ScoresAdapter scoresAdapter;
    private FencerResultAdapter resultAdapter;
    // RecyclerView
    public ArrayList<Bout> bouts = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private BoutRvAdapter mBoutsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // SETTING VALUES
    // Constant
    private static final int REQCODE_TO_SK = 1;
    // Variable
    private int NUM_FEN = 5;
    private int NUM_BOUTS = (NUM_FEN * (NUM_FEN - 1))/2;
    private int GRID_SIZE = NUM_FEN*NUM_FEN;
    private int SCORE_LIMIT = 5;
    private ScoreBox[] scores = new ScoreBox[GRID_SIZE];

    // TRACKING VALUES
    private static int activeBout = 0;
    private boolean scoreChanged = false;
    Bout oldBout = null;
    Bout changedBout = null;

    private Fencer[] fencerTemplate = {
        new Fencer("Alpha"),
        new Fencer("Bravo"),
        new Fencer("Charlie"),
        new Fencer("Delta"),
        new Fencer("Echo"),
        new Fencer("Foxtrot"),
        new Fencer("Golf"),
        new Fencer("Hotel")
    };

    private Fencer[] fencers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activeBout = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool);
        parseIntent();
        fencers = new Fencer[NUM_FEN];
        System.arraycopy(fencerTemplate, 0, fencers, 0, NUM_FEN);

        GridView scoresGrid = findViewById(R.id.gv_main_scores);
        buildBoutRV();

        ListView resultList = findViewById(R.id.lv_fencer_results);
        Button btnNextBout = findViewById(R.id.PL_btn_next_bout);
        btnNextBout.setOnClickListener(this);
        Button btnPrevBout = findViewById(R.id.PL_btn_redo);
        btnPrevBout.setOnClickListener(this);
        scoresGrid.setNumColumns(NUM_FEN);
        initializeScores();
        assignBouts();
        scoresAdapter = new ScoresAdapter(this, scores);
        resultAdapter = new FencerResultAdapter(this, fencers);
        scoresGrid.setAdapter(scoresAdapter);
        resultList.setAdapter(resultAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQCODE_TO_SK) {
            if (resultCode == RESULT_OK) {
                scoreChanged = true;
                placeBoutInArray(data);
                markPlaces();
                updateAdapters();
            }
        }
    }

    public void placeBoutInArray(Intent data) {
        Bout bout = data.getParcelableExtra(INTENT_BOUT);
        changedBout = bout;
        int boutIndex = data.getIntExtra(INTENT_BOUT_INDEX, 0);
        bouts.set(boutIndex, bout);
        addBout(bout);
        updateScores();
        mBoutsAdapter.notifyItemChanged(boutIndex);
        if (activeBout == boutIndex && activeBout < NUM_BOUTS-1) {
            while (activeBout < NUM_BOUTS && bouts.get(activeBout).isComplete()) {
                activeBout++;
            }
        }
        if (activeBout == NUM_BOUTS-1 && bouts.get(activeBout).isComplete()) {
            activeBout++;
        }
    }

    public void addBout(Bout bout) {
        fillScores(bout);
    }

    public void updateAdapters() {
        scoresAdapter.notifyDataSetChanged();
        resultAdapter.notifyDataSetChanged();
    }

    public void fillScores(Bout bout) {
        int myNumber = bout.getMyNumber();
        int opNumber = bout.getOpNumber();
        int myScore = bout.getMyScore();
        int opScore = bout.getOpScore();
        int myIndex = getIndex(myNumber, opNumber);
        int opIndex = getIndex(opNumber, myNumber);
        scores[myIndex].setScore(myScore);
        scores[opIndex].setScore(opScore);
        scores[myIndex].setShow(true);
        scores[opIndex].setShow(true);
    }

    private int getIndex(int myNumber, int opNumber) {
        return (myNumber * NUM_FEN) + opNumber;
    }

    private void addScores(@org.jetbrains.annotations.NotNull Bout bout, boolean positive) {
        int myNumber = bout.getMyNumber();
        int opNumber = bout.getOpNumber();
        int sign = positive ? 1 : -1;
        int myScore = sign * bout.getMyScore();
        int opScore = sign * bout.getOpScore();
        fencers[myNumber].addTS(myScore);
        fencers[myNumber].addTR(opScore);
        fencers[opNumber].addTS(opScore);
        fencers[opNumber].addTR(myScore);
        int myIndex = getIndex(myNumber, opNumber);
        int opIndex = getIndex(opNumber, myNumber);
        // How to deal with victories?
        if (!positive) {
            scores[myIndex].setVictory(false);
            scores[opIndex].setVictory(false);
        }
        if (bout.isComplete()) { // Subtracts "changed" bout only if bout had non-zero values
            if (bout.isMyVictory()) {
                fencers[myNumber].addVictory(sign);
                if (positive) scores[myIndex].setVictory(true);
            } else {
                fencers[opNumber].addVictory(sign);
                if (positive) scores[opIndex].setVictory(true);
            }
        }
    }

    private void addScores(Bout bout) {
        addScores(bout, true);
    }

    public void updateScores() {
        if (scoreChanged) {
            addScores(oldBout, false);
            scoreChanged = false;
        }
        addScores(changedBout);
    }

    private void assignBouts() {
        for (Bout bout : bouts) {
            bout.setMyName(fencers[bout.getMyNumber()].getLastName());
            bout.setOpName(fencers[bout.getOpNumber()].getLastName());
        }
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_NUM_FENCERS)) {
            int numFencersInt = intent.getIntExtra(INTENT_NUM_FENCERS, 5);
            if (numFencersInt > 0) {
                NUM_FEN = numFencersInt;
                NUM_BOUTS = (NUM_FEN * (NUM_FEN - 1)) / 2;
                GRID_SIZE = NUM_FEN * NUM_FEN;
                rebuildArrays();
            }
        }
        if (intent.hasExtra(INTENT_SCORE_LIMIT)) {
            int scoreLimitInt = intent.getIntExtra(INTENT_SCORE_LIMIT, 5);
            if (scoreLimitInt > 0) {
                SCORE_LIMIT = scoreLimitInt;
            } else {
                SCORE_LIMIT = 5;
            }
        }
    }

    private void rebuildArrays() {
        scores = new ScoreBox[GRID_SIZE];
        rebuildBouts();
    }

    private void rebuildBouts() {
        int[] list = new int[NUM_BOUTS];
        switch (NUM_FEN) {
            case 2:
                int[] list2 =
                        {12};
                list = list2;
                break;
            case 3:
                int[] list3 =
                        {12,13,23};
                list = list3;
                break;
            case 4:
                int[] list4 =
                        {14,23,13,24,34,12};
                list = list4;
                break;
            case 5:
                int[] list5 =
                        {12,34,51,23,54,13,25,41,35,42};
                list = list5;
                break;
            case 6:
                int[] list6 =
                        {12,45,23,56,31,64,25,14,53,16,42,36,51,34,62};
                list = list6;
                break;
            case 7:
                int[] list7 = {14,25,36,71,54,23,67,51,43,62,57,31,48,72,35,16,24,73,65,12,47};
                list = list7;
                break;
            case 8:
                int[] list8 = {23,15,74,68,12,34,56,87,41,52,83,67,42,81,75,36,28,74,61,37,48,26,35,17,46,85,72,13};
                list = list8;
                break;
        }
        int myNum;
        int opNum;
        for (int k=0; k<NUM_BOUTS; k++){
            myNum = (list[k]/10)-1;
            opNum = (list[k]%10)-1;
            bouts.add(new Bout(myNum, opNum));
        }

    }

    private void initializeScores() {
        for (int n=0; n<GRID_SIZE; n++) {
            ScoreBox sb = new ScoreBox();
            scores[n] = sb;
        }
        for (int d=0; d<NUM_FEN; d++) {
            scores[(d*NUM_FEN) + d].setBlack(true);
        }
    }

    private void markPlaces() {
        //sort fencers by victories, else indicator, descending
        Arrays.sort(fencers, Fencer.fencerPlacementComparator);
        System.out.println("SORTED");
        for (Fencer fencer : fencers) {
            System.out.println(fencer);
        }
        int place = 0;
        int prevVic = -1;
        int prevInd = -1;
        System.out.println("Placed");
        for (Fencer fencer : fencers) {
            if (prevVic != fencer.getVic() || prevInd != fencer.getInd()){
                place++;
            }
            fencer.setPlace(place);
            prevVic = fencer.getVic();
            prevInd = fencer.getInd();
            System.out.println(fencer);
        }
        // Sort by default (localIndex)
        Arrays.sort(fencers);
        System.out.println("ORIGINAL ORDER");
        for (Fencer fencer : fencers) {
            System.out.println(fencer);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.PL_btn_next_bout:
                if (activeBout < NUM_BOUTS) {
                    System.out.println("" +activeBout + ","+ NUM_BOUTS);
                    sendToScorekeeper();
                }
                break;
            case R.id.PL_btn_redo:
                if (activeBout > 0) {
                    prevToScorekeeper();
                }
        }
    }

    public void sendToScorekeeper(int boutNumber) {
        Bout activeBout = bouts.get(boutNumber);
        oldBout = activeBout;
        Intent intent = new Intent(ActivityPool.this, ActivityScorekeeper.class);
        intent.putExtra(INTENT_BOUT, activeBout);
        intent.putExtra(INTENT_BOUT_INDEX, boutNumber);
        startActivityForResult(intent, REQCODE_TO_SK);
    }

    public void sendToScorekeeper() {
        sendToScorekeeper(activeBout);
    }

    public void prevToScorekeeper() {
        Intent intent = new Intent(ActivityPool.this, ActivityScorekeeper.class);
        intent.putExtra(INTENT_BOUT, bouts.get(--activeBout));
        startActivityForResult(intent, REQCODE_TO_SK);
    }

    public void buildBoutRV() {
        mRecyclerView = findViewById(R.id.rv_bout_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mBoutsAdapter = new BoutRvAdapter(bouts);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mBoutsAdapter);

        mBoutsAdapter.setOnItemClickListener(new BoutRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                sendToScorekeeper(position);
            }
        });
    }
}
