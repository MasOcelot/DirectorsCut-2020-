package com.example.android.directorscut;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FencerPoolSetupAdapter extends RecyclerView.Adapter<FencerPoolSetupAdapter.FencerPoolViewHolder> {
    private ArrayList<Fencer>  mFencerList;

    public static class FencerPoolViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvRating;
        public TextView tvClub;
        public TextView tvIndex;

        public FencerPoolViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tv_c_local_num);
            tvName = itemView.findViewById(R.id.tv_c_fencer_name);
            tvRating = itemView.findViewById(R.id.tv_c_rating);
            tvClub = itemView.findViewById(R.id.tv_c_club);

        }
    }

    public FencerPoolSetupAdapter(ArrayList<Fencer> fencerArrayList) {
        mFencerList = fencerArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull FencerPoolViewHolder fencerPoolViewHolder, int i) {
        Fencer exampleFencer = mFencerList.get(i);

        String pos = String.valueOf(i + 1);

        fencerPoolViewHolder.tvIndex.setText(pos);
        fencerPoolViewHolder.tvName.setText(exampleFencer.getLastName());
        fencerPoolViewHolder.tvRating.setText(exampleFencer.getRatingAsString());
        fencerPoolViewHolder.tvClub.setText(exampleFencer.getClub());
    }

    @NonNull
    @Override
    public FencerPoolViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fencer_cardview, viewGroup, false);
        FencerPoolViewHolder fencerPoolVH = new FencerPoolViewHolder(v);
        return fencerPoolVH;
    }

    @Override
    public int getItemCount() {
        return mFencerList.size();
    }
}
