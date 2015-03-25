package com.example.mattias.gesaell;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Klass för att skapa en ny användare
 *
 */

public class NewUser extends Activity {

    private Button buttonTakePicture, buttonCreateUser;
    private ImageView imageView;
    private final int REQUEST_IMAGE_CAPTURE =112; // Int som är svarskod till onActivityResult
    private PictureObject pictureObject;
    private static int dummieId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        buttonTakePicture = (Button)findViewById(R.id.button_add_picture);
        buttonCreateUser = (Button)findViewById(R.id.button_create_user);
        imageView = (ImageView)findViewById(R.id.image_view);
        pictureObject = new PictureObject(imageView);

        //Lyssnare som skapar en ny intent för att ta en bild. sedan sparar onActivityResult den i databasen.
        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // Skapa intent för att ta bild.
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) { // kollar att intenten är genomförbar
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE); // Startar en  intent för att ta en bild och returnera den.
                }
            }
        });

        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pictureObject.showPicture();
            }
        });

    }

    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Kollar ifall det var result från image intenten, att allt gick bra och att det returnerade data.
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && null != data) {

                Bundle extras = data.getExtras(); // Hämta datan som intentet returnerade
                Bitmap imageBitmap = (Bitmap) extras.get("data"); // Gör det till en Bitmap

                imageView.setImageBitmap(imageBitmap); // Visa bilden i imageView
                if(pictureObject.addPicture(dummieId++, imageBitmap)) { // Lägg till bilden i databasen
                    Toast.makeText(this, "Bild tillagd i databas", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Bild kunde inte läggas till", Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(this, "Ingen bild tagen", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Någonting gick fel", Toast.LENGTH_LONG).show();
        }

    }



}
