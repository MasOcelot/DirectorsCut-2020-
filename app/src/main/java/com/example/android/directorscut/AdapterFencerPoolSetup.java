package com.example.android.directorscut;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterFencerPoolSetup extends RecyclerView.Adapter<AdapterFencerPoolSetup.FencerPoolViewHolder> {
    private ArrayList<Fencer>  mFencerList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onRemoveClick(int position);
        void onHandClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class FencerPoolViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvRating;
        public TextView tvClub;
        public TextView tvIndex;
        public TextView tvHand;
        public ImageView imRemove;

        public FencerPoolViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tv_c_local_num);
            tvName = itemView.findViewById(R.id.tv_c_fencer_name);
            tvRating = itemView.findViewById(R.id.tv_c_rating);
            tvClub = itemView.findViewById(R.id.tv_c_club);
            tvHand = itemView.findViewById(R.id.tv_c_hand);
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

            tvHand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!= null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onHandClick(position);
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
        Resources res = fencerPoolViewHolder.itemView.getResources();
        Fencer exampleFencer = mFencerList.get(i);

        String pos = (i + 1) + ".";
        fencerPoolViewHolder.tvIndex.setText(pos);
        fencerPoolViewHolder.tvName.setText(exampleFencer.getLastName());
        fencerPoolViewHolder.tvRating.setText(exampleFencer.getRatingAsString());
        fencerPoolViewHolder.tvClub.setText(exampleFencer.getClub());
        if (mFencerList.size() <= 2) {
            fencerPoolViewHolder.imRemove.setVisibility(View.INVISIBLE);
        } else {
            fencerPoolViewHolder.imRemove.setVisibility(View.VISIBLE);
        }

        if (exampleFencer.isLeftHanded()) {
            fencerPoolViewHolder.tvHand.setText("L");
            fencerPoolViewHolder.tvHand.setBackgroundDrawable(res.getDrawable(R.drawable.background_border_square_213));
        } else {
            fencerPoolViewHolder.tvHand.setText("R");
            fencerPoolViewHolder.tvHand.setBackgroundDrawable(res.getDrawable(R.drawable.background_border_square_212));
        }
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
