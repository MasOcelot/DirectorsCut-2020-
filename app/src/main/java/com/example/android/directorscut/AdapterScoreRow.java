package com.example.android.directorscut;

import android.content.res.Resources;
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
    private Resources mRes;

    public static class ScoreRowViewHolder extends RecyclerView.ViewHolder {
        private int nSize;
        public TextView tvName;
        public TextView tvBox0;
        public TextView tvBox1;
        public TextView tvBox2;
        public TextView tvBox3;
        public TextView tvBox4;
        public TextView tvBox5;
        public TextView tvBox6;
        public TextView tvBox7;

        public ScoreRowViewHolder(@NonNull View itemView, int size) {
            super(itemView);
            nSize = size;
            tvName = itemView.findViewById(R.id.cv_gr_name);
            tvBox0 = itemView.findViewById(R.id.gr_1);
            tvBox1 = itemView.findViewById(R.id.gr_2);
            setExtraViews();

        }

        private void setExtraViews() {
            switch (nSize) {
                case 8:
                    tvBox7 = itemView.findViewById(R.id.gr_8);
                case 7:
                    tvBox6 = itemView.findViewById(R.id.gr_7);
                case 6:
                    tvBox5 = itemView.findViewById(R.id.gr_6);
                case 5:
                    tvBox4 = itemView.findViewById(R.id.gr_5);
                case 4:
                    tvBox3 = itemView.findViewById(R.id.gr_4);
                case 3:
                    tvBox2 = itemView.findViewById(R.id.gr_3);
            }
        }

        public TextView getSBView(int viewNum) {
            try{
                switch (viewNum) {
                    case 7:
                        return tvBox7;
                    case 6:
                        return tvBox6;
                    case 5:
                        return tvBox5;
                    case 4:
                        return tvBox4;
                    case 3:
                        return tvBox3;
                    case 2:
                        return tvBox2;
                    case 1:
                        return tvBox1;
                    case 0:
                        return tvBox0;
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return null;
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
        mRes = scoreRowViewHolder.itemView.getResources();
        ScoreRow cScoreRow = mScoreRowList.get(i);
        String name = cScoreRow.getName();
        if (name.length() > 7) {
            scoreRowViewHolder.tvName.setTextSize(14);
        } else {
            scoreRowViewHolder.tvName.setTextSize(18);
        }
        String newName = "" + (i+1) + ". " + name;
        scoreRowViewHolder.tvName.setBackgroundColor(mRes.getColor(R.color.white));
        scoreRowViewHolder.tvName.setText(newName);
        scoreRowViewHolder.tvName.setTextColor(mRes.getColor(R.color.grid_ignore));

        for (int j = 0; j < mSize; j++) {
            ScoreBox currBox = cScoreRow.getScoreBox(j);
            TextView sbView = scoreRowViewHolder.getSBView(j);
            if (currBox.isBlack()) {
                sbView.setBackgroundDrawable(mRes.getDrawable(R.drawable.background_border_square_tea));
            } else {
                sbView.setBackgroundDrawable(mRes.getDrawable(R.drawable.background_border_square_tea_empty));
            }
            TextView boxJ = scoreRowViewHolder.getSBView(j);
            if (currBox.isShow()) {
                String scoreText = String.valueOf(currBox.getScore());
                if (currBox.isVictory()){
                    scoreText = "V" + scoreText;
                    boxJ.setTextColor(mRes.getColor(R.color.teaPrimaryDark));
                } else {
                    boxJ.setTextColor(mRes.getColor(R.color.grid_ignore));
                }
                if (scoreText.length() > 2) {
                    if (mSize > 7) {
                        boxJ.setTextSize(14);
                    } else if (mSize > 6) {
                        boxJ.setTextSize(16);
                    }
                }
                boxJ.setText(scoreText);

            } else {
                scoreRowViewHolder.getSBView(j).setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mScoreRowList.size();
    }


}
