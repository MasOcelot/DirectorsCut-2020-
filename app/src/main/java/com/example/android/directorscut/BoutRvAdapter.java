package com.example.android.directorscut;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BoutRvAdapter extends RecyclerView.Adapter<BoutRvAdapter.BoutViewHolder> {
    private ArrayList<Bout> mBoutList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class BoutViewHolder extends RecyclerView.ViewHolder {
        public TextView mIDsTV;
        public TextView mScoresTV;
        public TextView mNamesTV;

        public BoutViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mIDsTV = itemView.findViewById(R.id.tv_boutBox_IDs);
            mScoresTV = itemView.findViewById(R.id.tv_boutBox_scores);
            mNamesTV = itemView.findViewById(R.id.tv_boutBox_fullNames);

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

    public BoutRvAdapter(ArrayList<Bout> boutList) {
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
        Bout bout = mBoutList.get(i);

        String idText = "(" + (bout.getMyNumber()+1) + "-" + (bout.getOpNumber()+1) +")";
        boutViewHolder.mIDsTV.setText(idText);

        String namesText = "" + bout.getMyName()+" - " + bout.getOpName();
        boutViewHolder.mNamesTV.setText(namesText);

        if (bout.isComplete()) {
            String scoreText = "" + bout.getMyScore()+ "-" +bout.getOpScore();
            boutViewHolder.mScoresTV.setText(scoreText);
            boutViewHolder.mScoresTV.setTypeface(null, Typeface.BOLD);
            boutViewHolder.mScoresTV.setPaintFlags(boutViewHolder.mNamesTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            boutViewHolder.mNamesTV.setTypeface(null, Typeface.NORMAL);
            boutViewHolder.mNamesTV.setPaintFlags(boutViewHolder.mNamesTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            boutViewHolder.mScoresTV.setText("");
            boutViewHolder.mNamesTV.setTypeface(null, Typeface.BOLD);
            boutViewHolder.mNamesTV.setPaintFlags(boutViewHolder.mNamesTV.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount() {
        return mBoutList.size();
    }
}
