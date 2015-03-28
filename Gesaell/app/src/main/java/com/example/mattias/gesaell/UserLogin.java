package com.example.mattias.gesaell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Klass som fösöker logga in en användare.
 */

public class UserLogin extends Activity {

    private String url = "jdbc:mysql://atlas.dsv.su.se/db_14607937";
    private String user = "usr_14607937";
    private String password = "607937";

    Button buttonChat;
    DBConnection dbc;
    String username;
    String userPassword;
    ImageView userImage;
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent myIntent = getIntent();
        username = myIntent.getStringExtra("USERNAME");
        userPassword = myIntent.getStringExtra("PASSWORD");


        //Log.d("USERNAME FROM INTENT", username);

        UserTestLogin utl = new UserTestLogin();
        try {
            utl.execute(); // Försöker logga in
            if(utl.get()) { // kollar ifall det lyckades
                loginSuccess();
            }else{ // inloggning lyckades ej
                setContentView(R.layout.activity_main);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void loginSuccess(){

        setContentView(R.layout.user_loggedin);
        TextView userTextView = (TextView)findViewById(R.id.text_view_uname);
        userTextView.setText("Inloggad som: " +username);
        buttonChat = (Button) findViewById(R.id.button_chat);
        userImage = (ImageView) findViewById(R.id.user_image);

        SetUserImage sui = new SetUserImage();
        sui.execute();

        buttonChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(UserLogin.this, UserChat.class); // skapa ny aktivitet för chat
                myIntent.putExtra("USERNAME", username); // skicka med username som extra
                UserLogin.this.startActivity(myIntent);

            }
        });

        Button startGame = (Button)findViewById(R.id.button_start_game);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(UserLogin.this, Game.class); // skapa ny aktivitet
                UserLogin.this.startActivity(myIntent);

            }
        });

    }


    /**
     * Async task som försöker logga in
     * "returnerar" true vid lyckad inloggning, annars false.
     */
    private class UserTestLogin extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            dbc = new DBConnection(url, user, password);
            return dbc.userLogin(username, userPassword);
        }

        protected void onProgressUpdate(Void... progress) {}
    }

    /**
     * Async task som försöker sätta bilden från databasen.
     */
    private class SetUserImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            byte[] imageArr = dbc.getPicture(username);
            bitmap = BitmapFactory.decodeByteArray(imageArr, 0, imageArr.length);
            publishProgress();
            return null;
        }

        protected void onProgressUpdate(Void... progress) {
            userImage.setImageBitmap(bitmap);
        }
    }


}
