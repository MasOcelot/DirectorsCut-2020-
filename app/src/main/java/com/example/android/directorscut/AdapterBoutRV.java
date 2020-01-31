package com.example.android.directorscut;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterBoutRV extends RecyclerView.Adapter<AdapterBoutRV.BoutViewHolder> {
    private ArrayList<Bout> mBoutList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class BoutViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mCard;
        public TextView mIDsTV;
        public TextView mScoresTV;
        public TextView mNamesTV;
        private TextView mIndexTV;

        public BoutViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mCard = itemView.findViewById(R.id.rl_card);
            mIDsTV = itemView.findViewById(R.id.tv_boutBox_IDs);
            mScoresTV = itemView.findViewById(R.id.tv_boutBox_scores);
            mNamesTV = itemView.findViewById(R.id.tv_boutBox_fullNames);
            mIndexTV = itemView.findViewById(R.id.tv_boutBox_index);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public AdapterBoutRV(ArrayList<Bout> boutList) {
        mBoutList = boutList;
    }

    @NonNull
    @Override
    public BoutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_bout, viewGroup, false);
        BoutViewHolder bvh = new BoutViewHolder(view, mListener);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BoutViewHolder boutViewHolder, int i) {
        Resources reso = boutViewHolder.itemView.getResources();
        Bout bout = mBoutList.get(i);

        String idText = "(" + (bout.getMyNumber()+1) + "-" + (bout.getOpNumber()+1) +")";
        boutViewHolder.mIDsTV.setText(idText);

        String namesText = "" + bout.getMyName()+" - " + bout.getOpName();
        boutViewHolder.mNamesTV.setText(namesText);

        String indexText = (i+1) + ".";
        boutViewHolder.mIndexTV.setText(indexText);
        boutViewHolder.mIndexTV.setTypeface(null, Typeface.BOLD);

        int textColor;

        if (bout.isComplete()) {
            String scoreText = "" + bout.getMyScore()+ "-" +bout.getOpScore();
            boutViewHolder.mScoresTV.setText(scoreText);
            boutViewHolder.mScoresTV.setTypeface(null, Typeface.BOLD);
            boutViewHolder.mScoresTV.setPaintFlags(boutViewHolder.mNamesTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            boutViewHolder.mNamesTV.setTypeface(null, Typeface.NORMAL);
            boutViewHolder.mNamesTV.setPaintFlags(boutViewHolder.mNamesTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            boutViewHolder.mCard.setBackgroundResource(R.drawable.background_border_black);
            textColor = reso.getColor(R.color.corwh);
        } else {
            boutViewHolder.mScoresTV.setText("");
            boutViewHolder.mNamesTV.setTypeface(null, Typeface.BOLD);
            boutViewHolder.mNamesTV.setPaintFlags(boutViewHolder.mNamesTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            boutViewHolder.mCard.setBackgroundResource(R.drawable.background_border);
            textColor = reso.getColor(R.color.grid_ignore);
        }
        boutViewHolder.mIDsTV.setTextColor(textColor);
        boutViewHolder.mIndexTV.setTextColor(textColor);
        boutViewHolder.mNamesTV.setTextColor(textColor);
        boutViewHolder.mScoresTV.setTextColor(textColor);
    }



    @Override
    public int getItemCount() {
        return mBoutList.size();
    }
}
