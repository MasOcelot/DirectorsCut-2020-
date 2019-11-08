package com.example.android.directorscut;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FencerSetupAdapter extends RecyclerView.Adapter<FencerSetupAdapter.FencerSettingVH> {

    private ArrayList<Fencer> mFencerList; // 8

    public static class FencerSettingVH extends RecyclerView.ViewHolder {
        // 4
        public TextView mLocalIndex;
        public TextView mFencerName;
        public TextView mRating;
        public TextView mClub;

        public FencerSettingVH(@NonNull View itemView) {
            // 5
            super(itemView);
            mLocalIndex = itemView.findViewById(R.id.tv_c_local_ind);
            mFencerName = itemView.findViewById(R.id.tv_c_fencer_name);
            mRating = itemView.findViewById(R.id.tv_c_rating);
            mClub = itemView.findViewById(R.id.tv_c_club);
        }
    }

    public FencerSetupAdapter(ArrayList<Fencer> fencerList) { // 7
        mFencerList = fencerList; // 9
    }

    @NonNull
    @Override
    public FencerSettingVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 6
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fencer_cardview, viewGroup, false);
        FencerSettingVH fsVH = new FencerSettingVH(v);
        return fsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull FencerSettingVH fencerSettingVH, int i) {
        // 10
        Fencer currFencer = mFencerList.get(i);
        fencerSettingVH.mLocalIndex.setText(String.valueOf(currFencer.getLocalIndex() + 1) + ")");
        fencerSettingVH.mFencerName.setText(currFencer.getLastName());
        fencerSettingVH.mRating.setText(String.valueOf(currFencer.getRatingString()));
        fencerSettingVH.mClub.setText(currFencer.getClub());
    }

    // 11
    @Override
    public int getItemCount() {
        return mFencerList.size();
    }




}
