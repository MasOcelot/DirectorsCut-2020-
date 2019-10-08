package com.example.android.directorscut;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoresAdapter extends BaseAdapter {

    private final Context mContext;
    private final ScoreBox[] scoreArray;

    public ScoresAdapter(Context context, ScoreBox[] scoreArray) {
        this.mContext = context;
        this.scoreArray = scoreArray;
    }

    @Override
    public int getCount(){
        return scoreArray.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ScoreBox score = scoreArray[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_score, null);
        }

        final LinearLayout scoreBoxLinearLayout =
                (LinearLayout) convertView.findViewById(R.id.ll_scoreBox);
        final TextView victoryTextView =
                (TextView) convertView.findViewById(R.id.tv_scoreBox_victory);
        final Button scoreBoxButton =
                (Button) convertView.findViewById(R.id.btn_scoreBox_edit);

        if (score.isBlack()) {
//            scoreBoxLinearLayout.setBackgroundColor(Color.parseColor("#000000"));
            scoreBoxButton.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            String finalScore = "";
            if (score.isVictory()) {
                victoryTextView.setText("Victory");
                finalScore += "V";
            }
            if (score.isShow()) {
                finalScore += Integer.toString(score.getScore());
                scoreBoxButton.setText(finalScore);
            }
        }
        return convertView;
    }
}
