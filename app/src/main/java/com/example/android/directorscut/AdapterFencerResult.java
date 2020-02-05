package com.example.android.directorscut;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterFencerResult extends BaseAdapter {

    private final Context mContext;
    private final Fencer[] fencers;

    public AdapterFencerResult(Context context, Fencer[] fencers) {
        this.mContext = context;
        this.fencers = fencers;
    }

    @Override
    public int getCount() {
        return fencers.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final Fencer fencer = fencers[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.listview_fencer, null);
        }
        Resources reso = convertView.getResources();

        final LinearLayout llBackGround =
                (LinearLayout) convertView.findViewById(R.id.ll_fencer_results);
        final TextView numTextView =
                (TextView) convertView.findViewById(R.id.tv_result_fencerNum);
        final TextView victoriesTextView =
                (TextView) convertView.findViewById(R.id.tv_result_v);
        final TextView tsTextView =
                (TextView) convertView.findViewById(R.id.tv_result_ts);
        final TextView trTextView =
                (TextView) convertView.findViewById(R.id.tv_result_tr);
        final TextView indTextView =
                (TextView) convertView.findViewById(R.id.tv_result_ind);
        final TextView plTextView =
                (TextView) convertView.findViewById(R.id.tv_result_pl);

        Drawable bgDrawable;
        int bgColor;
        int textColor;
        if (position % 2 == 0) {
            bgDrawable = reso.getDrawable(R.drawable.background_border_square_212);
            bgColor = reso.getColor(R.color.teaPrimaryLight);
            textColor = reso.getColor(R.color.grid_ignore);
        } else {
            bgDrawable = reso.getDrawable(R.drawable.background_border_square_white);
            bgColor = reso.getColor(R.color.white);
            textColor = reso.getColor(R.color.grid_ignore);
        }
        llBackGround.setBackgroundColor(bgColor);

        String fencerNumber = "" + (position + 1) + ". ";
        numTextView.setText(fencerNumber);
        numTextView.setTextColor(reso.getColor(R.color.grid_ignore));
        numTextView.setBackgroundColor(bgColor);

        String fencerV = "" + fencer.getVic();
        String fencerTS = "" + fencer.getTS();
        String fencerTR = "" + fencer.getTR();
        String fencerInd = "" + fencer.getInd();
        String fencerPl = "" + fencer.getPlace();

        victoriesTextView.setText(fencerV);
        victoriesTextView.setTextColor(textColor);
        victoriesTextView.setBackgroundColor(bgColor);
        tsTextView.setText(fencerTS);
        tsTextView.setTextColor(textColor);
        tsTextView.setBackgroundColor(bgColor);
        trTextView.setText(fencerTR);
        trTextView.setTextColor(textColor);
        trTextView.setBackgroundColor(bgColor);
        indTextView.setText(fencerInd);
        indTextView.setTextColor(textColor);
        indTextView.setBackgroundColor(bgColor);
        plTextView.setText(fencerPl);
        plTextView.setTextColor(reso.getColor(R.color.grid_ignore));
//        plTextView.setBackgroundDrawable(bgDrawable);

        return convertView;
    }

}
