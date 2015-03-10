package com.example.mattias.ipm33;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {

    private Button buttonStartRecording, buttonPlay;
    private int CODE = 12; // Random kod
    private Uri lastVideoUri; // sista inspelade film uri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartRecording = (Button)findViewById(R.id.button);
        buttonPlay = (Button)findViewById(R.id.button2);

        buttonStartRecording.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Skapar en intent för att spela in video.
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                //Startar intenten för att få resultat med resultatKoden.
                startActivityForResult(takeVideoIntent, CODE);
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastVideoUri != null) { // Kolla att det redan spelats in en video
                    // Skapa intent för att spela upp video
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lastVideoUri.toString()));
                    //starta intent
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Spela in en video först", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CODE){ // Kollar att det är rätt resultat
            if(resultCode == RESULT_OK) { // Kollar att allt gick bra
                lastVideoUri = data.getData(); // Sätter senaste intepelade uri
            }else{
                Toast.makeText(this, "Igen inspelning", Toast.LENGTH_LONG).show();
            }
        }
    }


}
