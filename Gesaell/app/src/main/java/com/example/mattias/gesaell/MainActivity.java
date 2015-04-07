package com.example.mattias.gesaell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
                Intent newUserIntent = new Intent(MainActivity.this, NewUser.class);
                MainActivity.this.startActivity(newUserIntent);
            }
        });

        // Lyssnare för att logga in
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                if(username != "" && password != "") { // Kollar att både användarnamn och lösenord är angivet
                    Intent loginIntent = new Intent(MainActivity.this, UserLogin.class); // skapa ny aktivitet
                    loginIntent.putExtra("USERNAME", username); // skicka med username som extra
                    loginIntent.putExtra("PASSWORD", password); // skicka med password som extra
                    MainActivity.this.startActivity(loginIntent);
                }

            }
        });

    }


}
