package com.example.android.directorscut;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterScoreRow extends RecyclerView.Adapter<AdapterScoreRow.ScoreRowViewHolder> {
    private static final int ROW_2 = 2;
    private static final int ROW_3 = 3;
    private ArrayList<ScoreRow> mScoreRowList;
    private int mSize;

    public static class ScoreRowViewHolder extends RecyclerView.ViewHolder {
        private int nSize;
        public TextView tvName;
        public TextView tvBox1;
        public TextView tvBox2;
        public TextView tvBox3;
        public TextView tvBox4;
        public TextView tvBox5;
        public TextView tvBox6;
        public TextView tvBox7;
        public TextView tvBox8;

        public ScoreRowViewHolder(@NonNull View itemView, int size) {
            super(itemView);
            nSize = size;
            tvName = itemView.findViewById(R.id.cv_gr_name);
            tvBox1 = itemView.findViewById(R.id.gr_1);
            tvBox2 = itemView.findViewById(R.id.gr_2);
            setExtraViews();

        }

        private void setExtraViews() {
            switch (nSize) {
                case 8:
                    tvBox8 = itemView.findViewById(R.id.gr_8);
                case 7:
                    tvBox7 = itemView.findViewById(R.id.gr_7);
                case 6:
                    tvBox6 = itemView.findViewById(R.id.gr_6);
                case 5:
                    tvBox5 = itemView.findViewById(R.id.gr_5);
                case 4:
                    tvBox4 = itemView.findViewById(R.id.gr_4);
                case 3:
                    tvBox3 = itemView.findViewById(R.id.gr_3);
            }
        }

    }

    public AdapterScoreRow(ArrayList<ScoreRow> scoreRowList, int size) {
        mScoreRowList = scoreRowList;
        mSize = size;
    }

    @NonNull
    @Override
    public ScoreRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int res = getCardType();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(res, viewGroup, false);
        return new ScoreRowViewHolder(v, mSize);
    }

    private int getCardType() {
        int res;
        switch (mSize) {
            case 8:
                res = R.layout.cardview_pl8;
                break;
            case 7:
                res = R.layout.cardview_pl7;
                break;
            case 6:
                res = R.layout.cardview_pl6;
                break;
            case 5:
                res = R.layout.cardview_pl5;
                break;
            case 4:
                res = R.layout.cardview_pl4;
                break;
            case 3:
                res = R.layout.cardview_pl3;
                break;
            default:
                res = R.layout.cardview_pl2;
        }
        return res;
    }



    @Override
    public void onBindViewHolder(@NonNull ScoreRowViewHolder scoreRowViewHolder, int i) {
        ScoreRow cScoreRow = mScoreRowList.get(i);
        scoreRowViewHolder.tvName.setText(cScoreRow.getName());
        switch (mSize) {
            case 8:
                if(cScoreRow.getBox8().isShow()) {
                    String victoryText = String.valueOf(cScoreRow.getBox8().getScore());
                    victoryText = cScoreRow.getBox8().isVictory() ? "V" + victoryText : victoryText;
                    scoreRowViewHolder.tvBox8.setText(victoryText);
                }
//                if(cScoreRow.getBox8().isBlack()) scoreRowViewHolder.tvBox8.setBackgroundColor(Color.parseColor("#000000"));
            case 7:
                if(cScoreRow.getBox7().isShow()) {
                    String victoryText = String.valueOf(cScoreRow.getBox7().getScore());
                    victoryText = cScoreRow.getBox7().isVictory() ? "V" + victoryText : victoryText;
                    scoreRowViewHolder.tvBox7.setText(victoryText);
                }
//                if(cScoreRow.getBox7().isBlack()) scoreRowViewHolder.tvBox7.setBackgroundColor(Color.parseColor("#000000"));
            case 6:
                if(cScoreRow.getBox6().isShow()) {
                    String victoryText = String.valueOf(cScoreRow.getBox6().getScore());
                    victoryText = cScoreRow.getBox6().isVictory() ? "V" + victoryText : victoryText;
                    scoreRowViewHolder.tvBox6.setText(victoryText);
                }
//                if(cScoreRow.getBox6().isBlack()) scoreRowViewHolder.tvBox6.setBackgroundColor(Color.parseColor("#000000"));
            case 5:
                if(cScoreRow.getBox5().isShow()) {
                    String victoryText = String.valueOf(cScoreRow.getBox5().getScore());
                    victoryText = cScoreRow.getBox5().isVictory() ? "V" + victoryText : victoryText;
                    scoreRowViewHolder.tvBox5.setText(victoryText);
                }
//                if(cScoreRow.getBox5().isBlack()) scoreRowViewHolder.tvBox5.setBackgroundColor(Color.parseColor("#000000"));
            case 4:
                if(cScoreRow.getBox4().isShow()) {
                    String victoryText = String.valueOf(cScoreRow.getBox4().getScore());
                    victoryText = cScoreRow.getBox4().isVictory() ? "V" + victoryText : victoryText;
                    scoreRowViewHolder.tvBox4.setText(victoryText);
                }
//                if(cScoreRow.getBox4().isBlack()) scoreRowViewHolder.tvBox4.setBackgroundColor(Color.parseColor("#000000"));
            case 3:
                if(cScoreRow.getBox3().isShow()) {
                    String victoryText = String.valueOf(cScoreRow.getBox3().getScore());
                    victoryText = cScoreRow.getBox3().isVictory() ? "V" + victoryText : victoryText;
                    scoreRowViewHolder.tvBox3.setText(victoryText);
                }
//                if(cScoreRow.getBox3().isBlack()) scoreRowViewHolder.tvBox3.setBackgroundColor(Color.parseColor("#000000"));
            default:
                if(cScoreRow.getBox2().isShow()) {
                    String victoryText = String.valueOf(cScoreRow.getBox2().getScore());
                    victoryText = cScoreRow.getBox2().isVictory() ? "V" + victoryText : victoryText;
                    scoreRowViewHolder.tvBox2.setText(victoryText);
                }
//                if(cScoreRow.getBox2().isBlack()) scoreRowViewHolder.tvBox2.setBackgroundColor(Color.parseColor("#000000"));
                if(cScoreRow.getBox1().isShow()) {
                    String victoryText = String.valueOf(cScoreRow.getBox1().getScore());
                    victoryText = cScoreRow.getBox1().isVictory() ? "V" + victoryText : victoryText;
                    scoreRowViewHolder.tvBox1.setText(victoryText);
                }
//                if(cScoreRow.getBox1().isBlack()) scoreRowViewHolder.tvBox1.setBackgroundColor(Color.parseColor("#000000"));
        }
//        switch (i) {
//            case 7:
//
//                break;
//            case 6:
//                scoreRowViewHolder.tvBox7.setBackgroundColor(Color.parseColor("#000000"));
//                break;
//            case 5:
//                scoreRowViewHolder.tvBox6.setBackgroundColor(Color.parseColor("#000000"));
//                break;
//            case 4:
//                scoreRowViewHolder.tvBox5.setBackgroundColor(Color.parseColor("#000000"));
//                break;
//            case 3:
//                scoreRowViewHolder.tvBox4.setBackgroundColor(Color.parseColor("#000000"));
//                break;
//            case 2:
//                scoreRowViewHolder.tvBox3.setBackgroundColor(Color.parseColor("#000000"));
//                break;
//            case 1:
//                scoreRowViewHolder.tvBox2.setBackgroundColor(Color.parseColor("#000000"));
//                break;
//            case 0:
//                scoreRowViewHolder.tvBox1.setBackgroundColor(Color.parseColor("#000000"));
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return mScoreRowList.size();
    }


}
