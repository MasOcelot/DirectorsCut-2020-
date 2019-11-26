package com.example.android.directorscut;

import android.content.Intent;
import android.support.annotation.Nullable;
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
    private static final String INTENT_FENCER_EDIT = "fencer_edit";
    private static final String INTENT_FENCER_INDEX = "fencer_index";
    private static final String INTENT_FENCER_ARRAY = "fencer_array";
    private static final int REQ_FENCER_EDIT = 5;

    // VIEWS
    // Upper
    private ArrayList<Fencer> mPsFencers;
    private EditText mEtNumFencers;
    private EditText mEtScoreLimit;
    private Button mBtnAddFencer;
    private int mNumFencers = 5;
    private int mScoreLimit = 15;
    // RecyclerView
    private RecyclerView mRvFencer;
    private FencerSetupAdapter mFencerAdapter;
    private RecyclerView.LayoutManager mLmFencer;
    // Lower
    private Button mBtnBoutOnly;
    private Button mBtnStartPool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_setup);
        createFencerList();
        setupButtons();
        setupViews();
        bindRV();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_FENCER_EDIT) {
            if (resultCode == RESULT_OK) {
                placeFencerInArray(data);
            }
        }
    }

    public void placeFencerInArray(Intent data) {
        Fencer fencer = data.getParcelableExtra(INTENT_FENCER_EDIT);
        int fencerIndex = data.getIntExtra(INTENT_FENCER_INDEX, 0);
        mPsFencers.set(fencerIndex, fencer);
        mFencerAdapter.notifyItemChanged(fencerIndex);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ps_btn_add_fencer:
                addFencer();
                break;
            case R.id.btn_bout_only:
                startActivityBoutOnly();
                break;
            case R.id.btn_start_pool:
                startActivityPool();
                break;
        }
    }

    private void addFencer() {
        if (mPsFencers.size() <= 7){
            mPsFencers.add(new Fencer());
            mFencerAdapter.notifyItemInserted(mPsFencers.size());
        }
    }

    public void createFencerList() {
        mPsFencers = new ArrayList<Fencer>(){{
            add(new Fencer());
            add(new Fencer());
            add(new Fencer());
            add(new Fencer());
        }};
    }

    // 12 & 13 & 14
    private void bindRV() {
        mRvFencer = findViewById(R.id.rv_fencer_list);
        mRvFencer.hasFixedSize();
        mLmFencer = new LinearLayoutManager(this);
        mFencerAdapter = new FencerSetupAdapter(mPsFencers);
        mRvFencer.setLayoutManager(mLmFencer);
        mRvFencer.setAdapter(mFencerAdapter);

        mFencerAdapter.setOnItemClickListener(new FencerSetupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //open fencer detail edit
                Intent intent = new Intent(ActivityPoolSetup.this, ActivityFencerDetail.class);
                intent.putExtra(INTENT_FENCER_EDIT, mPsFencers.get(position));
                intent.putExtra(INTENT_FENCER_INDEX, position);

                startActivityForResult(intent, REQ_FENCER_EDIT);
            }
        });
    }

    private void setupViews() {
        mEtScoreLimit = findViewById(R.id.et_score_limit);
    }

    private void setupButtons() {
        mBtnAddFencer = findViewById(R.id.ps_btn_add_fencer);
        mBtnBoutOnly = findViewById(R.id.btn_bout_only);
        mBtnStartPool = findViewById(R.id.btn_start_pool);
        mBtnAddFencer.setOnClickListener(this);
        mBtnBoutOnly.setOnClickListener(this);
        mBtnStartPool.setOnClickListener(this);
    }

   private void startActivityBoutOnly() {
       Intent skIntent = new Intent(ActivityPoolSetup.this, ActivityScorekeeper.class);
       String scoreLimitText = mEtScoreLimit.getText().toString();
       if (!scoreLimitText.equals("")) {
           int scoreLimitInt = Integer.parseInt(scoreLimitText);
           if (scoreLimitInt > 0) {
               mScoreLimit = scoreLimitInt;
           } else {
               mScoreLimit = 0;
           }
       }
       skIntent.putExtra(INTENT_SCORE_LIMIT, mScoreLimit);

       startActivity(skIntent);
   }

   private void startActivityPool() { Intent plIntent = new Intent(ActivityPoolSetup.this, ActivityPool.class);
       String scoreLimitText = mEtScoreLimit.getText().toString();
       if (!scoreLimitText.equals("")) {
           int scoreLimitInt = Integer.parseInt(scoreLimitText);
           if (scoreLimitInt > 0) {
               mScoreLimit = scoreLimitInt;
           } else {
               mScoreLimit = 5;
           }
       } else {
           mScoreLimit = 5;
       }
       plIntent.putExtra(INTENT_NUM_FENCERS, mPsFencers.size());
       plIntent.putExtra(INTENT_SCORE_LIMIT, mScoreLimit);
       plIntent.putParcelableArrayListExtra(INTENT_FENCER_ARRAY, mPsFencers);
       Toaster.makeToast(this, "Starting Pool!");
       startActivity(plIntent);
   }
}
