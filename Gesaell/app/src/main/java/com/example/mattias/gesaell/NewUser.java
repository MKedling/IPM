package com.example.mattias.gesaell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private UserObject userObject;
    private EditText inputUsername, inputPassword;
    private Bitmap userImage;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        buttonTakePicture = (Button)findViewById(R.id.button_add_picture);
        buttonCreateUser = (Button)findViewById(R.id.button_create_user);
        imageView = (ImageView)findViewById(R.id.image_view);
        inputUsername = (EditText)findViewById(R.id.input_username);
        inputPassword = (EditText)findViewById(R.id.input_password);

        //Lyssnare som skapar en ny intent för att ta en bild som sparas i en variabel.
        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // Skapa intent för att ta bild.
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) { // kollar att intenten är genomförbar
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE); // Startar en  intent för att ta en bild och returnera den.
                }

            }
        });

        //Lyssnare som anropar metod som lägger till användare i databasen
        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(addUserDataToDB()){ // kollar ifall tillägget lyckades.
                    setContentView(R.layout.activity_main);
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Kollar ifall det var result från image intenten, att allt gick bra och att det returnerade data.
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && null != data) {

                Bundle extras = data.getExtras(); // Hämta datan som intentet returnerade
                userImage = (Bitmap) extras.get("data"); // Gör det till en Bitmap

                imageView.setImageBitmap(userImage); // Visa bilden i imageView

            } else {
                Toast.makeText(this, "Ingen bild tagen", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Någonting gick fel", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Metod som skapar ett UserObject och sedan anropar metod hos det som lägger till anv i databasen.
     * @return true vid lyckat tilläggande, annars false.
     */
    protected boolean addUserDataToDB(){

        username = inputUsername.getText().toString();
        password = inputPassword.getText().toString();

        if(username != null && password != null && userImage != null) { // Kolla att ingen variabel är null
            userObject = new UserObject(imageView, username, password, userImage); // Skapa nytt user objekt

            if (userObject.addUserToDB()) { // Lägg usern i databasen
                Toast.makeText(this, "Användare tillagd i databas", Toast.LENGTH_LONG).show();
                return true;
            } else {
                Toast.makeText(this, "Användare kunde inte läggas till", Toast.LENGTH_LONG).show();
            }
        }

        return false;
    }



}
