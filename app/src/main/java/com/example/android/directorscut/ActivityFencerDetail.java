package com.example.android.directorscut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityFencerDetail extends AppCompatActivity implements View.OnClickListener {

    // INTENTS
    private static final String INTENT_FENCER_EDIT = "fencer_edit";
    private static final String INTENT_FENCER_INDEX = "fencer_index";
    private static final int REQ_FENCER_EDIT = 5;

    // VIEWS
    private TextView mTvIndex;
    private EditText mEtName;
    private EditText mEtRating;
    private EditText mEtRYear;
    private EditText mEtClub;

    private Button mSaveFencer;

    private Fencer mFencer;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fencer_detail);

        Intent intent = getIntent();
        mFencer = intent.getParcelableExtra(INTENT_FENCER_EDIT);
        mIndex = intent.getIntExtra(INTENT_FENCER_INDEX, 0);
        initViews();
        initButtons();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fd_btn_save:
                finishIntent();
                break;
        }
    }

    private void initButtons() {
        mSaveFencer = findViewById(R.id.fd_btn_save);
        mSaveFencer.setOnClickListener(this);
    }

    private void initViews() {
        String index = "Fencer " + String.valueOf(mFencer.getLocalIndex()+1);
        String name = mFencer.getLastName();
        String club = mFencer.getClub();
        String rating = mFencer.getRatingString();
        int rYear = mFencer.getRatingYear();

        mTvIndex = findViewById(R.id.fd_tv_index);
        mTvIndex.setText(index);
        mEtName = findViewById(R.id.fd_et_name);
        mEtName.setHint(name);
        mEtClub = findViewById(R.id.fd_et_club);
        mEtClub.setHint(club);
        mEtRating = findViewById(R.id.fd_et_rating);
        mEtRating.setHint(rating);
        mEtRYear = findViewById(R.id.fd_et_ratingyear);
        if (rYear > 0) mEtRYear.setHint(String.valueOf(rYear));
    }

    private void finishIntent() {
        applyChanges();
        Intent resultFD = new Intent(this, ActivityPoolSetup.class);
        resultFD.putExtra(INTENT_FENCER_EDIT, mFencer);
        resultFD.putExtra(INTENT_FENCER_INDEX, mIndex);
        setResult(RESULT_OK, resultFD);
        finish();
    }

    private void applyChanges() {
        String newName = String.valueOf(mEtName.getText());
        String newClub = String.valueOf(mEtClub.getText());
        String ratingString = String.valueOf(mEtRating.getText());
        String ratingYear = String.valueOf(mEtRYear.getText());

        if (!ratingString.isEmpty()) {
            int newRating = Integer.parseInt(ratingString);
            mFencer.setRating(newRating);
            if (mFencer.getRating() == 0) {
                mFencer.setRatingYear(0);
            } else {
                if (!ratingYear.isEmpty()) {
                    mFencer.setRatingYear(Integer.parseInt(ratingYear));
                }
            }
        }
        if (!newName.isEmpty()) mFencer.setLastName(newName);
        if (!newClub.isEmpty()) mFencer.setClub(newClub);

    }

    private int convertRating(String strRating) {
        switch (strRating) {
            case "U":
                return 0;
            case "E":
                return 1;
            case "D":
                return 2;
            case "C":
                return 3;
            case "B":
                return 4;
            case "A":
                return 5;
        }
        return 0;
    }

}
