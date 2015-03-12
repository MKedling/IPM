package com.example.mattias.ipm711;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ClipboardManager myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        Button buttonCopy = (Button) findViewById(R.id.button_copy);
        Button buttonPaste = (Button) findViewById(R.id.button_paste);
        final TextView tw = (TextView) findViewById(R.id.text_view);
        final EditText inputString = (EditText)findViewById(R.id.edit_text);

        buttonCopy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClipData myClip;
                String text = inputString.getText().toString(); // Hämtar input från edittext
                myClip = ClipData.newPlainText("text", text); // skapar ett clipdata object med mimetypen MIMETYPE_TEXT_PLAIN.
                myClipboard.setPrimaryClip(myClip); // Sätter clipboards primära clip
                Toast.makeText(MainActivity.this, "Kopierat till clipboard", Toast.LENGTH_SHORT).show();

            }
        });

        buttonPaste.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClipData abc = myClipboard.getPrimaryClip(); // hämtar clipboarddata från clipboard
                ClipData.Item item = abc.getItemAt(0); // Hämtar första Item från Clipboard Data
                String text = item.getText().toString(); // Hämtar texten från Clipboard data
                tw.setText(text); // Visar texten i textviewn
            }
        });



    }



}
