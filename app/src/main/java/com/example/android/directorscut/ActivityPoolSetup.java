package com.example.android.directorscut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ActivityPoolSetup extends AppCompatActivity implements View.OnClickListener {
    // INTENTS
    private static final String INTENT_NUM_FENCERS = "number_fencers";
    private static final String INTENT_SCORE_LIMIT = "score_limit";

    // VIEWS
    private Button btnBoutOnly;
    private Button btnStartPool;
    // Upper
    private EditText etNumFencers;
    private EditText etScoreLimit;
    private int numFencers = 5;
    private int scoreLimit = 0;
    // Lower
    private RecyclerView mRvFencer;
    private RecyclerView.Adapter mFencerAdapter;
    private RecyclerView.LayoutManager mLmFencer;

    ArrayList<Fencer> psFencers = new ArrayList<Fencer>(){{
        add(new Fencer("Mas", 1, 2017));
        add(new Fencer("Sam"));
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_setup);
        setupButtons();
        setupViews();
        bindRV();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_bout_only:
                startActivityBoutOnly();
                break;
            case R.id.btn_start_pool:
                startActivityPool();
                break;
        }
    }

    // 12 & 13 & 14
    private void bindRV() {
        mRvFencer = findViewById(R.id.rv_fencer_list);
        mRvFencer.hasFixedSize();
        mLmFencer = new LinearLayoutManager(this);
        mFencerAdapter = new FencerSetupAdapter(psFencers);
        mRvFencer.setLayoutManager(mLmFencer);
        mRvFencer.setAdapter(mFencerAdapter);
    }

    private void setupViews() {
        etNumFencers = findViewById(R.id.et_num_fencers);
        etScoreLimit = findViewById(R.id.et_score_limit);
    }

    private void setupButtons() {
        btnBoutOnly = findViewById(R.id.btn_bout_only);
        btnStartPool = findViewById(R.id.btn_start_pool);
        btnBoutOnly.setOnClickListener(this);
        btnStartPool.setOnClickListener(this);
    }

   private void startActivityBoutOnly() {
       Intent skIntent = new Intent(ActivityPoolSetup.this, ActivityScorekeeper.class);
       String scoreLimitText = etScoreLimit.getText().toString();
       if (!scoreLimitText.equals("")) {
           int scoreLimitInt = Integer.parseInt(scoreLimitText);
           if (scoreLimitInt > 0) {
               scoreLimit = scoreLimitInt;
           } else {
               scoreLimit = 0;
           }
       }
       skIntent.putExtra(INTENT_SCORE_LIMIT, scoreLimit);

       startActivity(skIntent);
   }

   private void startActivityPool() {
        Intent plIntent = new Intent(ActivityPoolSetup.this, ActivityPool.class);
        String numFencersText = etNumFencers.getText().toString();
        if (!numFencersText.equals("")) {
            int numFencersInt = Integer.parseInt(numFencersText);
            if (numFencersInt > 0) {
                numFencers = numFencersInt;
            } else {
                numFencers = 5;
            }
        }
       String scoreLimitText = etScoreLimit.getText().toString();
       if (!scoreLimitText.equals("")) {
           int scoreLimitInt = Integer.parseInt(scoreLimitText);
           if (scoreLimitInt > 0) {
               scoreLimit = scoreLimitInt;
           } else {
               scoreLimit = 5;
           }
       } else {
           scoreLimit = 5;
       }
       plIntent.putExtra(INTENT_NUM_FENCERS, numFencers);
       plIntent.putExtra(INTENT_SCORE_LIMIT, scoreLimit);
       startActivity(plIntent);
   }
}
