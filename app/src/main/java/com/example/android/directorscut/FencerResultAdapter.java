package com.example.android.directorscut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FencerResultAdapter extends BaseAdapter {

    private final Context mContext;
    private final Fencer[] fencers;

    public FencerResultAdapter(Context context, Fencer[] fencers) {
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
            convertView = layoutInflater.inflate(R.layout.linearlayout_results, null);
        }

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

        String fencerNumber = "" + (position + 1) + ". ";
        numTextView.setText(fencerNumber);

        String fencerV = "" + fencer.getVic();
        String fencerTS = "" + fencer.getTS();
        String fencerTR = "" + fencer.getTR();
        String fencerInd = "" + fencer.getInd();
        String fencerPl = "" + fencer.getPlace();

        victoriesTextView.setText(fencerV);
        tsTextView.setText(fencerTS);
        trTextView.setText(fencerTR);
        indTextView.setText(fencerInd);
        plTextView.setText(fencerPl);

        return convertView;
    }

}
