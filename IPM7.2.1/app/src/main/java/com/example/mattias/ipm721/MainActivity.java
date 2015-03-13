package com.example.mattias.ipm721;

import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements MoodDialog.MoodDialogListener {

    private int mId = 12222455; // Id för att identifiera notifikationen
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button_show_dialog);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(mId); // Raderar eventuell existerande notifikation så den försvinner då användaren klickar på den

        //Listener för att skapa och visa dialogen.
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new MoodDialog();
                newFragment.show(getFragmentManager(), "Mood dialog");
            }
        });
    }

    //Metoder från interfacet för att få data returnerad från dialogfragment klassen
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "Hen e glad, glad, glad",Toast.LENGTH_SHORT).show();
        showNotification("Hen är glad xD!");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Hen e arg!! :/",Toast.LENGTH_SHORT).show();
        showNotification("Hen är arg :/ ...!");
    }

    //Metod för att skapa en notifikation men angiven text.
    private void showNotification(String text){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_btn_check_to_on_mtrl_000) // Sätt ikon
                        .setContentTitle("Humör svar !")    // Sätt rubrik
                        .setContentText(text); // Sätt innehåll

        // Skapa intent för denna klass
        Intent resultIntent = new Intent(this, MainActivity.class);

        //Kod från andorid developer för att skapa backstack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        // Visa notifikation
        notificationManager.notify(mId, mBuilder.build());
    }


}
