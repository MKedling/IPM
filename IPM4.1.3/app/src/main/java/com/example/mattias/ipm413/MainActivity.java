package com.example.mattias.ipm413;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.File;

public class MainActivity extends Activity {

    private final int CODE = 43; // Slumpmässig kod för att identifiera onActivityResult
    private Uri URI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSend = (Button) findViewById(R.id.button_send);
        Button buttonAttatch = (Button) findViewById(R.id.button_attatch);
        final EditText inputReceiver = (EditText) findViewById(R.id.input_email_address);
        final EditText inputSubject = (EditText) findViewById(R.id.input_subject);
        final EditText inputMessage = (EditText) findViewById(R.id.input_message);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String[] receiver = {inputReceiver.getText().toString()};
                String subject = inputSubject.getText().toString();
                String message = inputMessage.getText().toString();

                Intent sendMail = new Intent(Intent.ACTION_SEND);
                sendMail.setData(Uri.parse("mail:to")); // Sätter protokollet som skall användas

                //Sätter mottagare, ämne och meddelande
                sendMail.putExtra(Intent.EXTRA_EMAIL, receiver);
                sendMail.putExtra(Intent.EXTRA_SUBJECT, subject);
                sendMail.putExtra(Intent.EXTRA_TEXT, message);
                sendMail.setType("message/rfc822"); // sätter mime typen  till email.

                if (URI != null) { // kollar ifall användaren valt någon fil.
                    sendMail.putExtra(Intent.EXTRA_STREAM, URI);
                }

                sendMail.createChooser(sendMail, "Send Email"); // Låter användaren välja app att sända mail med
                startActivity(sendMail);

            }
        });

        //Listener för att välja en bild
        buttonAttatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentPickImage = new Intent();
                intentPickImage.setAction(Intent.ACTION_GET_CONTENT); // Användaren får välja fil från en picker
                intentPickImage.setType("image/*"); // Talar om att det är en bildfil som skall väljas
                intentPickImage.putExtra("return-data", true);
                startActivityForResult(Intent.createChooser(intentPickImage, "Complete action using"), CODE);
            }
        });

    }

    //Kod från internet för att hantera svaret från ACTION_GET_CONTENT
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(filePathColumn[0]);
            String attachment = cursor.getString(index);
            Log.e("Attachment Path:", attachment);
            URI = Uri.parse("file://" + attachment);
            cursor.close();
        }
    }



}
