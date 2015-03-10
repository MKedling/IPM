package com.example.mattias.ipm411;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button buttonVid1, buttonVid2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonVid1 = (Button) findViewById(R.id.button1);
        buttonVid2 = (Button) findViewById(R.id.button2);

        buttonVid1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Skapaer en Uri från en string
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=HhHcuvhPStw");
                //Skapar en intent som skall visa en video med uri'n
                Intent viewYoutube = new Intent(Intent.ACTION_VIEW, uri);
                //Kör intenten
                startActivity(viewYoutube);
            }
        });

        buttonVid2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=2Wo_QfQZ0lE&list=PLAwxTw4SYaPmoZn7y5jS9pU9GmocKZuzY&index=1")));
            }
        });

    }



}
