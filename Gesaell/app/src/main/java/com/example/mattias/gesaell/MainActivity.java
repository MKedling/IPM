package com.example.mattias.gesaell;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;


public class MainActivity extends Activity {

    private String url = "jdbc:mysql://atlas.dsv.su.se/db_14607937";
    private String user = "usr_14607937";
    private String password = "607937";
    private TextView tw;
    private Button button, buttonInsertPicture, buttonGetPicture;
    private String name = "TEst";
    private ImageView imageView, imageView2;
    private Bitmap b;
    private byte[] array;
    private int dummieId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tw = (TextView)findViewById(R.id.text_view);
        button = (Button)findViewById(R.id.button);
        buttonInsertPicture = (Button)findViewById(R.id.button2);
        buttonGetPicture = (Button)findViewById(R.id.button3);
        imageView = (ImageView)findViewById(R.id.image_view);
        imageView2 = (ImageView)findViewById(R.id.image_view2);
        imageView.setImageResource(R.mipmap.ic_launcher);

        //Hämtar bilden från imageview och gör den till en Bitmap.
        b = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        // Resettar textview eller hämtar entrys från servern varannan gång.
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(tw.getText().toString() == "") {
                    new UpdateList().execute();
                }else{
                    tw.setText("");
                }
            }
        });

        buttonInsertPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Bitmap to byte array
                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                array = stream.toByteArray();

                new AddPicture().execute();
            }
        });

        buttonGetPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new GetPicture().execute();
            }
        });

    }

    // Asynctask för att hämta data från servern.
    private class UpdateList extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            DBConnection dbc = new DBConnection(url, user, password);
            String upd = dbc.getAllPersons();
            publishProgress(upd);
            return null;
        }
        //Metoden som körs i UI tråden och visar bilden från servern.
        protected void onProgressUpdate(String... progress) {
            tw.setText(progress[0]);
        }

    }

    // Asynctask för att lägga till bild i databas
    private class AddPicture extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            DBConnection dbc = new DBConnection(url, user, password);
            dbc.insertPicture(dummieId++, array);
            return null;
        }
        //Metoden som körs i UI tråden och visar bilden från servern.
        protected void onProgressUpdate(Void... progress) {

        }


    }

    // Asynctask för att lägga till bild i databas
    private class GetPicture extends AsyncTask<Void, Void, Void> {
        byte[] byteArryResponse;
        @Override
        protected Void doInBackground(Void... params) {

            DBConnection dbc = new DBConnection(url, user, password);
            byteArryResponse = dbc.getPicture();
            publishProgress();
            return null;
        }
        //Metoden som körs i UI tråden och visar bilden från servern.
        protected void onProgressUpdate(Void... progress) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArryResponse, 0, byteArryResponse.length);
            imageView2.setImageBitmap(bitmap);

        }

    }






}
