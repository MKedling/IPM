package com.example.mattias.gesaell;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    private Button buttonLogin, buttonNewUser;
    private EditText inputUsername, inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogin = (Button)findViewById(R.id.button_login);
        buttonNewUser = (Button)findViewById(R.id.button_new_user);
        inputUsername = (EditText) findViewById(R.id.login_input_username);
        inputPassword = (EditText) findViewById(R.id.login_input_password);

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, NewUser.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        // Lyssnare för att logga in
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                Log.d("INPUT USERNAME", username);
                Log.d("INPUT PASSWORD", password);

                if(username != "" && password != "") {
                    Intent myIntent = new Intent(MainActivity.this, UserLogin.class); // skapa ny aktivitet
                    myIntent.putExtra("USERNAME", username); // skicka med username som extra
                    myIntent.putExtra("PASSWORD", password); // skicka med password som extra
                    MainActivity.this.startActivity(myIntent);
                }

            }
        });

    }



}
