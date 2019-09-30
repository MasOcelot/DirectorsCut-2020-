package com.example.android.directorscut;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Pool extends AppCompatActivity {

    private static final String FOTL_INTENT = "score_fotl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool);

        int count = getIntent().getIntExtra(FOTL_INTENT, 0);
        generateGrid(count);
    }

    public void generateGrid (Integer numFencers) {
        final LinearLayout grid = findViewById(R.id.poolGrid);

        for (int i=0; i<numFencers; i++) {
            // Row LinLayout (Horizontal)
            LinearLayout fencerRow = new LinearLayout(this);
            fencerRow.setOrientation(LinearLayout.HORIZONTAL);

            // TextView Number
            TextView number = new TextView(this);
            number.setText("" + i);
            fencerRow.addView(number);

            // TextView Name
            TextView fencerName = new TextView(this);
            fencerName.setText("Name"+i);
            fencerRow.addView(fencerName);

            // Button Array
            LinearLayout fencerScores = new LinearLayout(this);
            fencerScores.setOrientation(LinearLayout.HORIZONTAL);
            fencerScores.setWeightSum(numFencers);

            // Add Cells
            LinearLayout.LayoutParams btnParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            for (int j=0; j<numFencers; j++) {
                Button opponent = new Button(this);
                if (i==j) {
                    opponent.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    opponent.setText(" ");
                } else {
                    opponent.setText("-");
                }
                opponent.setLayoutParams(btnParam);
                fencerScores.addView(opponent);

            }
            fencerRow.addView(fencerScores);

            grid.addView(fencerRow);
        }
    }



}
