package com.example.mattias.ipm62;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = (Button) findViewById(R.id.button_send);
        final TextView inputNumber = (TextView) findViewById(R.id.input_phone_number);
        final TextView inputMessage = (TextView) findViewById(R.id.input_message);

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNumber = inputNumber.getText().toString();
                String message = inputMessage.getText().toString();
                //skapar en ny intent för att skicka sms med parsade telefonnummret som argument
                Intent sendSmsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
                //Lägger till meddelandet
                sendSmsIntent.putExtra("sms_body", message);
                startActivity(sendSmsIntent);
            }
        });


    }



}
