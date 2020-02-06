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

public class DialogSKEditTime extends AppCompatDialogFragment {
    private EditText editTextMinutes;
    private EditText editTextSeconds;

    private DialogEditTimeListener editTimeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builderTimer = new AlertDialog.Builder(getActivity());

        LayoutInflater inflaterTimer = getActivity().getLayoutInflater();
        View view = inflaterTimer.inflate(R.layout.layout_dialog_time, null);

        builderTimer.setView(view)
                .setTitle("Set Time")
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Action", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String minute = editTextMinutes.getText().toString();
                        String second = editTextSeconds.getText().toString();

                        if (minute.length() <= 0 && second.length() <= 0) {
                            return;
                        }

                        if (second.length() <= 0) {
                            second = "00";
                        }
                        if (minute.length() <= 0) {
                            minute = "00";
                        }

                        editTimeListener.applyActEditTime(minute, second);
                    }
                })
                .setPositiveButton("Main", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String minute = editTextMinutes.getText().toString();
                        String second = editTextSeconds.getText().toString();

                        if (minute.length() <= 0 && second.length() <= 0) {
                            return;
                        }

                        if (second.length() <= 0) {
                            second = "00";
                        }
                        if (minute.length() <= 0) {
                            minute = "00";
                        }

                        editTimeListener.applyEditTime(minute, second);
                    }
                });

        editTextMinutes = view.findViewById(R.id.et_time_minute);
        editTextSeconds = view.findViewById(R.id.et_time_second);

        return builderTimer.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            editTimeListener = (DialogEditTimeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogEditTimeListener");
        }
    }

    // Interface
    public interface DialogEditTimeListener {
        void applyEditTime(String minute, String second);
        void applyActEditTime(String minute, String second);
    }
}
