package com.example.mattias.extraipm423;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

public class MainActivity extends Activity {

    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSend = (Button) findViewById(R.id.button);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageResource(R.mipmap.ic_launcher);

        //Hämtar bilden från imageview och gör den till en Bitmap.
        final Bitmap b = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        //Bitmap to byte array
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] array = stream.toByteArray();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                 new ConnectionToServer().execute(stream);

            }
        });

    }

    private class ConnectionToServer extends AsyncTask<ByteArrayOutputStream, Storage, Void> {

        ObjectInputStream in; // Ska den vara static ??
        ObjectOutputStream out;

        //ObjectInputStream in;
        @Override
        protected Void doInBackground(ByteArrayOutputStream... params) {

            Storage returnStorage = null;

            try{
                Socket socket = new Socket("atlas.dsv.su.se", 4848); // Connectar till servern

                Storage storage = new Storage(params[0]);// Hämtar storage som skickats som parameter

                out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(storage); // Skickar storage objektet till servern

                out.flush(); //Skickar headern som input streamen behöver men fungerar inte iaf

                //Här stannar exekveringen, nästa rad verkar inte fungera.
                // Googlat och det står de säger byt plats på in och out men det fungerar inte.
                // Säger även att getInputStream() blocker tills out har skrivit och flushar header.
                //Därför testade jag out.flush() men ingen skillnad.
                // onProgress fungerar dock.
                in = new ObjectInputStream(socket.getInputStream()); // Instansierar inputstream

                returnStorage = (Storage)in.readObject(); // Väntar på ett paket från servern

                publishProgress(returnStorage);// Uppdatera UI tråden
                out.close();
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;

        }
        //Metoden som körs i UI tråden och visar bilden från servern.
        protected void onProgressUpdate(Storage... progress) {
            super.onProgressUpdate(progress);
            byte[] array = progress[0].getData(); // Hämtar byte arrayen från storage objektet som skickades som parameter
            Bitmap bmp = BitmapFactory.decodeByteArray(array, 0, array.length); // Byte array to bmp
            imageView2.setImageBitmap(bmp); // Visar bilden för användaren.
        }


    }


}
