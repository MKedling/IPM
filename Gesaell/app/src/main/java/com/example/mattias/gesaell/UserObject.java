package com.example.mattias.gesaell;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;

public class UserObject {

    private String url = "jdbc:mysql://atlas.dsv.su.se/db_14607937";
    private String user = "usr_14607937";
    private String password = "607937";
    private String userName, userPassword;
    private Bitmap userImage;

    private byte[] array;
    private ImageView imageView;

    /**
     * Konstruktor för nya användare.
     * @param imageView iamgeView'n från NewUser
     * @param userName - användarnamnet som skall skapas
     * @param userPassword - lösenordet till användarnamnet
     * @param userImage - bilden från användaren som skall lagras
     */
    public UserObject(ImageView imageView, String userName, String userPassword, Bitmap userImage){
        this.imageView = imageView;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userImage = userImage;

        // lägg till allt i databasen
    }

    /**
     * Konstruktor för redan registrerade användare
     * @param imageView iamgeView'n från NewUser
     * @param userName - användarnamn
     * @param userPassword - lösenord
     */
    public UserObject(ImageView imageView, String userName, String userPassword){
        this.imageView = imageView;
        this.userName = userName;
        this.userPassword = userPassword;

    }


    /**
     * Metod för att lägga till en användare i databasen.
     * @return true ifall användaren blev tillagd annars false.
     */
    protected boolean addUserToDB(){
        //Bitmap to byte array
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        userImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        array = stream.toByteArray();
        AddUserToDB add = new AddUserToDB();

        try {
            return add.execute().get();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    protected void showPicture(){
        new GetPicture().execute();
    }

    /**
     * Async task för att lägga till en användare i databasen
     * "returnerar" true ifall det lyckades, annars false.
     */
    private class AddUserToDB extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            DBConnection dbc = new DBConnection(url, user, password);
            return dbc.insertNewUser(userName, userPassword, array);
        }

        protected void onProgressUpdate(Void... progress) {}
    }

    // Asynctask för att hämta bild från databas
    private class GetPicture extends AsyncTask<Void, Void, Void> {
        byte[] byteArryResponse;
        @Override
        protected Void doInBackground(Void... params) {

            DBConnection dbc = new DBConnection(url, user, password);
            byteArryResponse = dbc.getPicture(userName);
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
