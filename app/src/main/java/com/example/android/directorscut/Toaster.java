package com.example.android.directorscut;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    private Toaster(){}

    public static void makeToast(Context context,  String message) {
        Toast aToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        aToast.show();
    }
}
