package com.example.mattias.ipm721;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class MoodDialog extends DialogFragment {

    //Interface för att skicka data till MainActivity
    public interface MoodDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    MoodDialogListener moodDialogListener;

    // Instansierar dialog listenern, kod lik den hos developer.android.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Kollar att aktiviteten implementar interfacet
        try {
            // Instansierar interfacet så data kan skickas till main activity
            moodDialogListener = (MoodDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " måste implementera interfacet MoodDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Är du glad?")
                .setPositiveButton("Jajamen !!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Returnera att användaren klickade på den possitiva knappen
                        moodDialogListener.onDialogPositiveClick(MoodDialog.this);
                    }
                })
                .setNegativeButton("Nej :(", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Returnera att användaren klickade på den negativa knappen
                        moodDialogListener.onDialogNegativeClick(MoodDialog.this);
                    }
                });
        return builder.create();
    }

}
