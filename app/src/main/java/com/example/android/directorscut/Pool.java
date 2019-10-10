package com.example.android.directorscut;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Pool extends AppCompatActivity {

    private static final String INTENT_FOTL_SCORE = "score_fotl";
    private final int NUM_FEN = 5;
    private final int GRID_SIZE = NUM_FEN*NUM_FEN;
    private ScoreBox[] scores = new ScoreBox[GRID_SIZE];
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool);
//        int count = getIntent().getIntExtra(INTENT_FOTL_SCORE, 0);
        assignBouts();
        GridView scoresGrid = findViewById(R.id.gv_main_scores);
        ListView boutList = findViewById(R.id.lv_bout_list);
        ListView resultList = findViewById(R.id.lv_fencer_results);
        scoresGrid.setNumColumns(NUM_FEN);
        randomScores();
        initializeScores();
        updateScores();
        markPlaces();
        ScoresAdapter scoresAdapter = new ScoresAdapter(this, scores);
        scoresGrid.setAdapter(scoresAdapter);
        BoutListAdapter boutsAdapter = new BoutListAdapter(this, bouts);
        boutList.setAdapter(boutsAdapter);
        FencerResultAdapter resultAdapter = new FencerResultAdapter(this, fencers);
        resultList.setAdapter(resultAdapter);

    }

    public void fillScores(Bout bout, int numFencers) {
        if (bout.isComplete()) {
            int myNumber = bout.getMyNumber();
            int opNumber = bout.getOpNumber();
            int myScore = bout.getMyScore();
            int opScore = bout.getOpScore();
            int myIndex = (myNumber * numFencers) + opNumber;
            int opIndex = (opNumber * numFencers) + myNumber;
            fencers[myNumber].addTS(myScore);
            fencers[myNumber].addTR(opScore);
            fencers[opNumber].addTS(opScore);
            fencers[opNumber].addTR(myScore);
            scores[myIndex].setScore(myScore);
            scores[opIndex].setScore(opScore);
            scores[myIndex].setShow(true);
            scores[opIndex].setShow(true);
            if (bout.isMyVictory()) {
                fencers[myNumber].addVictory();
                scores[myIndex].setVictory(true);
            } else {
                fencers[opNumber].addVictory();
                scores[opIndex].setVictory(true);
            }
        }
    }

    private void assignBouts() {
        for (Bout bout : bouts) {
            bout.setMyName(fencers[bout.getMyNumber()].getLastName());
            bout.setOpName(fencers[bout.getOpNumber()].getLastName());
        }
    }

    private Bout[] bouts = {
        new Bout(0,1),
        new Bout(2,3),
        new Bout(4,0),
        new Bout(1,2),
        new Bout(4,3),
        new Bout(0,2),
        new Bout(1,4),
        new Bout(3,0),
        new Bout(2,4),
        new Bout(3,1)
    };

    private Fencer[] fencers = {
            new Fencer("Alpha"),
            new Fencer("Bravo"),
            new Fencer("Charlie"),
            new Fencer("Delta"),
            new Fencer("Echo")
    };

    private void updateScores() {
        for (Bout bout : bouts) {
            if (bout.isComplete()) fillScores(bout, NUM_FEN);
        }
        for (Fencer fencer : fencers) {
            System.out.println(fencer);
        }
    }

    private void getFencerPlace() {
        for (Fencer fencer : fencers) {
            ;
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

    private void randomScores() {
        for (Bout bout : bouts){
            boolean coinFlip = random.nextBoolean();
            if (true) {
                bout.setComplete(true);
            }
            int theirs = 5;
            int mine = random.nextInt(6);
            if (mine == 5) {
                while (theirs == 5) theirs = random.nextInt(6);
            }
            bout.setMyScore(mine);
            bout.setOpScore(theirs);
        }
    }

    private void markPlaces() {
        //sort fencers by indicator, descending
        //sort fencers by victories, descending
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

        Arrays.sort(fencers);
        System.out.println("ORIGINAL ORDER");
        for (Fencer fencer : fencers) {
            System.out.println(fencer);
        }
    }




}
