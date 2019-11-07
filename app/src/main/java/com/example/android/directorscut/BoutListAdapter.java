package com.example.android.directorscut;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BoutListAdapter extends BaseAdapter {

    private final Context mContext;
    private final Bout[] boutList;

    public BoutListAdapter(Context context, Bout[] boutList) {
        this.mContext = context;
        this.boutList = boutList;
    }

    @Override
    public int getCount() {
        return boutList.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Bout bout = boutList[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.cardview_bout, null);
        }

        final TextView idTextView =
                (TextView) convertView.findViewById(R.id.tv_boutBox_IDs);
        final TextView scoreTextView =
                (TextView) convertView.findViewById(R.id.tv_boutBox_scores);
        final TextView namesTextView =
                (TextView) convertView.findViewById(R.id.tv_boutBox_fullNames);

        int myRealNumber = bout.getMyNumber() + 1;
        int opRealNumber = bout.getOpNumber() + 1;
        int realPosition = position + 1;
        String idString = "" + realPosition + ". (" + myRealNumber + "-" + opRealNumber + ")";
        String nameString = "" + bout.getMyName() + " - " + bout.getOpName();
        idTextView.setText(idString);
        namesTextView.setText(nameString);

        if (bout.isComplete()) {
            String scoreString = "" + bout.getMyScore() + " - " + bout.getOpScore();
            scoreTextView.setText(scoreString);
            scoreTextView.setTypeface(null, Typeface.BOLD);
            scoreTextView.setPaintFlags(namesTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            namesTextView.setTypeface(null, Typeface.NORMAL);
            namesTextView.setPaintFlags(namesTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            scoreTextView.setText("");
            namesTextView.setTypeface(null, Typeface.BOLD);
            namesTextView.setPaintFlags(namesTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

        }

        return convertView;
    }
}
