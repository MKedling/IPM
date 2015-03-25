package com.example.mattias.gesaell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button buttonNewUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonNewUser = (Button)findViewById(R.id.button_new_user);

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, NewUser.class);
                MainActivity.this.startActivity(myIntent);
            }
        });



    }






}
