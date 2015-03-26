package com.example.mattias.extraipm421;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


// Klass som ansluter till en server. Tar emot och skickar meddelanden till servern.

public class MainActivity extends Activity {

    ConnectionToServer serverConnectionThread; // Async thread som sköter Sockets
    TextView chatWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatWindow = (TextView)findViewById(R.id.textView);
        final TextView input = (TextView)findViewById(R.id.editText);
        Button b = (Button) findViewById(R.id.button);

        // Lyssnare som anropar sendMsg i async thread klassen för att skicka meddelande till servern.
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                serverConnectionThread.sendMsg(input.getText().toString());
            }
        });

        serverConnectionThread = new ConnectionToServer();
        serverConnectionThread.execute();

    }

    // Innre klass som körs i bakgrunden och hanterar anslutningen till servern.
    private class ConnectionToServer extends AsyncTask<Void, String, Void> {

        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String host = "atlas.dsv.su.se";
        private int port = 9494;

        @Override
        protected Void doInBackground(Void... params) {
            //Skapar en connection till servern för att läsa och skriva.
            try {
                socket = new Socket(host, port);
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //Runmetod som väntar på meddelanden från servern och publiserar dem i UI tråden.
                while(true){
                    try {
                        String msg = in.readLine(); // Blockerande, väntar på meddelanden
                        publishProgress(msg); // Publicera i UI tråden
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
           return null;
        }
        //Metoden som körs i UI tråden och uppdaterar chatfönstret.
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            chatWindow.append("\nInc:  " + progress[0]);
        }
        // Metod som sänder en string till servern.
        protected void sendMsg(String msg){
            out.println(msg);
        }

    }



}
