package com.example.android.directorscut;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

public class DialogSKVictor extends AppCompatDialogFragment {
    private DialogSKVictorListener listener;

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Tie Breaker")
                .setMessage("Who won the bout?")
                .setNeutralButton("Left", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onLeftClicked();
                    }
                })
                .setPositiveButton("Right", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onRightClicked();
                    }
                });
        return builder.create();
    }

    public interface DialogSKVictorListener {
        void onRightClicked();
        void onLeftClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogSKVictorListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement DialogSKVictorListener");
        }

    }
}
