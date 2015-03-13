package com.example.mattias.ipm722;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStartVibrate = (Button) findViewById(R.id.button_start);
        Button buttonStopVibrate = (Button) findViewById(R.id.button_stop);
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        buttonStartVibrate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrator.vibrate(5000); // Vibrera 5 sek
            }
        });

        buttonStopVibrate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrator.cancel(); // Sluta vibrera
            }
        });


    }



}
