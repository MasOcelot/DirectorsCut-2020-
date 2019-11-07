package com.example.android.directorscut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityCard extends AppCompatActivity implements View.OnClickListener {
    private static final String INTENT_CARD_NAME = "card_name";
    private static final String INTENT_CARD_SIDE = "card_side";
    private static final String INTENT_CARD_TYPE = "card_type";

    private TextView fencerName;
    private TextView fencerSide;
    private LinearLayout cardColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        setupViews();
        extractIntents();
        cardColor.setOnClickListener(this);
    }

    private void setupViews() {
        fencerName = (TextView) findViewById(R.id.tv_card_name);
        fencerSide = (TextView) findViewById(R.id.tv_card_side);
        cardColor = (LinearLayout) findViewById(R.id.LL_card);
    }

    private void extractIntents() {
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_CARD_NAME)) {
            String cardName = intent.getStringExtra(INTENT_CARD_NAME);
            fencerName.setText(cardName);
        }
        if (intent.hasExtra(INTENT_CARD_SIDE)) {
            String cardSide = intent.getStringExtra(INTENT_CARD_SIDE);
            fencerSide.setText(cardSide);
        }
        if (intent.hasExtra(INTENT_CARD_TYPE)) {
            int cardType = intent.getIntExtra(INTENT_CARD_TYPE, 0);
            int colorBG = 0;
            int colorTX = getResources().getColor(android.R.color.black);
            switch (cardType) {
                case 0:
                    colorBG = getResources().getColor(R.color.card_yellow);
                    break;
                case 1:
                    colorBG = getResources().getColor(R.color.card_red);
                    break;
                case 2:
                    colorBG = getResources().getColor(R.color.card_black);
                    colorTX = getResources().getColor(android.R.color.white);
                    break;
            }
            cardColor.setBackgroundColor(colorBG);
            fencerName.setTextColor(colorTX);
            fencerSide.setTextColor(colorTX);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LL_card:
                finish();
        }
    }
}
