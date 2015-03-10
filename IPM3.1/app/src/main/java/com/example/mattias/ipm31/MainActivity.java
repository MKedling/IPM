package com.example.mattias.ipm31;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//Uppgift 3.1 som tar en bild med camera appen och sedan visar den för användaren
public class MainActivity extends Activity {

    private Button buttonTakePic;
    private Bitmap bmp;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTakePic = (Button) findViewById(R.id.button_take_picture);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);

        // Sätter upp en click listener som skapar en intent för att ta en bild.
        // Startar sedan intentet och tar emot resultatet
        buttonTakePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Intent intentTakePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(intentTakePic, 0);
            }
        });
    }

    //Metod för att ta emot resultatet.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) { //Kollar att allt gick bra, annars gör ingenting
            Bundle extra = data.getExtras(); // Allt extra från intenten som startActivityForResult returnerade
            bmp = (Bitmap) extra.get("data"); //Hämtar bilden som startActivityForResult har lagt dit med nyckeln "data"
            textView.setText("Senast tagna bild");
            imageView.setImageBitmap(bmp); // Visar bilden i imageViewn
        }else{
            textView.setText("Kunde ej ta ny bild");
        }

    }


}
