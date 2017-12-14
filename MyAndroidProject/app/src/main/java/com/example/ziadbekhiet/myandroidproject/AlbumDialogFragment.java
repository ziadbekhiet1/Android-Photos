package com.example.ziadbekhiet.myandroidproject;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class AlbumDialogFragment extends DialogFragment {
    public static final String MESSAGE_KEY = "message_key";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Bundle bundle = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(bundle.getString(MESSAGE_KEY))
                .setPositiveButton("OK", (dialog,id) -> {});

        // Create the AlertDialog object and return it
        return builder.create();
    }


}

