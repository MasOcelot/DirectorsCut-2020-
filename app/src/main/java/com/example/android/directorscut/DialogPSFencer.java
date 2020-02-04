package com.example.android.directorscut;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class DialogPSFencer extends AppCompatDialogFragment {
    private EditText etName;
    private EditText etClub;
    private Spinner spRating;
    private Spinner spYear;

    private DialogFencerListener dfListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fencer, null);

        builder.setView(view)
                .setTitle("Fencer Details")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = etName.getText().toString();
                        String club = etClub.getText().toString();
                        dfListener.applyFencerChanges(name, club);
                    }
                });

        etName = view.findViewById(R.id.et_df_name);
        etClub = view.findViewById(R.id.et_df_club);
        spRating = view.findViewById(R.id.sp_df_rating);
        spYear = view.findViewById(R.id.sp_df_year);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dfListener = (DialogFencerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogFencerListener");
        }
    }

    public interface DialogFencerListener {
        void applyFencerChanges(String name, String club, FencerRating.Rating ratingLet, int ratingYear);
        void applyFencerChanges(String name, String club);
    }

}

