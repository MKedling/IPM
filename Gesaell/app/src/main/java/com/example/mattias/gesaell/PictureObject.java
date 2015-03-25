package com.example.mattias.gesaell;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;

public class PictureObject {

    private String url = "jdbc:mysql://atlas.dsv.su.se/db_14607937";
    private String user = "usr_14607937";
    private String password = "607937";

    private byte[] array;
    private ImageView imageView;

    public PictureObject(ImageView imageView){
        this.imageView = imageView;
    }


    /**
     * Metod för att lägga till en bild i databasen i form av en byte[].
     * @param id't på användaren
     * @param bilden som skall läggas till.
     * @return
     */
    protected boolean addPicture(int userId, Bitmap b){
        //Bitmap to byte array
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
        array = stream.toByteArray();
        AddPicture add = new AddPicture();

        try {
            return add.execute(userId).get();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    protected void showPicture(){
        new GetPicture().execute();
    }

    // Asynctask för att lägga till bild i databas
    private class AddPicture extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... params) {
            DBConnection dbc = new DBConnection(url, user, password);
            return dbc.insertPicture(params[0], array);
        }

        protected void onProgressUpdate(Void... progress) {

        }
    }

    // Asynctask för att hämta bild från databas
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
            imageView.setImageBitmap(bitmap);

        }

    }


}
