package com.example.mattias.ipm32;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button buttonStartRecord;
    private int CODE = 12; //Slumpmässig resultatkod

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartRecord= (Button) findViewById(R.id.button_start_record);

        buttonStartRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Skapar en intent för att spela in ljud.
                Intent intentStartRecord = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                //Startar intenten för att få resultat med resultatKoden.
                startActivityForResult(intentStartRecord, CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CODE){ // Kollar att det är rätt resultat
            if(resultCode == RESULT_OK) { // Kollar att allt gick bra
                Uri savedUri = data.getData(); // Hämtar uri som intentets data pekar på
                Toast.makeText(this, "Inspelad", Toast.LENGTH_LONG).show();
                MediaPlayer mediaPlayer = MediaPlayer.create(this, savedUri); // Skapar mediaspelare med uri som sökväg
                mediaPlayer.start(); // Startar mediaspelaren/uppspelningen
            }else{
                Toast.makeText(this, "Igen inspelning", Toast.LENGTH_LONG).show();
            }
        }
    }


}
