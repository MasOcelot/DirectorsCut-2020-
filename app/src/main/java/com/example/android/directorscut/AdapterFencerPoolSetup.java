package com.example.android.directorscut;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterFencerPoolSetup extends RecyclerView.Adapter<AdapterFencerPoolSetup.FencerPoolViewHolder> {
    private ArrayList<Fencer>  mFencerList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class FencerPoolViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvRating;
        public TextView tvClub;
        public TextView tvIndex;
        public ImageView imRemove;

        public FencerPoolViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tv_c_local_num);
            tvName = itemView.findViewById(R.id.tv_c_fencer_name);
            tvRating = itemView.findViewById(R.id.tv_c_rating);
            tvClub = itemView.findViewById(R.id.tv_c_club);
            imRemove = itemView.findViewById(R.id.im_c_remove);

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

            imRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRemoveClick(position);
                        }
                    }
                }
            });

        }
    }

    public AdapterFencerPoolSetup(ArrayList<Fencer> fencerArrayList) {
        mFencerList = fencerArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull FencerPoolViewHolder fencerPoolViewHolder, int i) {
        Fencer exampleFencer = mFencerList.get(i);

        String pos = (i + 1) + ".";
        fencerPoolViewHolder.tvIndex.setText(pos);
        fencerPoolViewHolder.tvName.setText(exampleFencer.getLastName());
        fencerPoolViewHolder.tvRating.setText(exampleFencer.getRatingAsString());
        fencerPoolViewHolder.tvClub.setText(exampleFencer.getClub());
//        if (mFencerList.size() <= 2) {
//            fencerPoolViewHolder.imRemove.setBackgroundColor();
//        }
    }

    @NonNull
    @Override
    public FencerPoolViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_fencer, viewGroup, false);
        return new FencerPoolViewHolder(v, mListener);
    }

    @Override
    public int getItemCount() {
        return mFencerList.size();
    }
}
