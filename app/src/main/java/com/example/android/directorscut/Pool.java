package com.example.android.directorscut;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Pool extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool);
        generateGrid(4);
    }

    public void generateGrid (Integer numFencers) {
        final LinearLayout grid = findViewById(R.id.poolGrid);

        LinearLayout.LayoutParams gridParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        );

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
            for (int j=0; j<numFencers; j++) {
                Button opponent = new Button(this);
                if (i==j) {
                    opponent.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    opponent.setText(" ");
                } else {
                    opponent.setText("-");
                }
                fencerRow.addView(opponent);
            }

            grid.addView(fencerRow);
        }
    }



}
