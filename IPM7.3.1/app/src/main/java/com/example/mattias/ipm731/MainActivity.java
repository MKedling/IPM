package com.example.mattias.ipm731;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        final TextView tw = (TextView) findViewById(R.id.textView);

        //Klick lyssnare för att visa om användaren är ansluten till internet och via vilken typ.
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ConnectivityManager cManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cManager.getActiveNetworkInfo();

                tw.setText("");
                tw.append("Ansluten: "  + info.isConnected() + "\n");
                tw.append("Typ: "  + info.getTypeName() + "\n");

            }
        });

    }



}
