package com.example.mattias.gesaell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import java.util.List;
import java.util.Locale;

/**
 * Klass som implementerar en chat där användaren kan sända och motta meddelanden
 * Användarens användarnamn och location skickas med i meddelanden.
 */
public class UserChat extends Activity {

    LocationManager locationManager;
    double longitude, latitude;
    String userLocation = null;
    String username;
    TextView chatWindow;
    ConnectionToServer serverConnectionThread;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent myIntent = getIntent();
        username = myIntent.getStringExtra("USERNAME"); // hämtar username

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener); // Lyssnar efter location uppdateringar

        Button buttonSendMsg = (Button) findViewById(R.id.button_send_message);
        chatWindow = (TextView) findViewById(R.id.textView);
        final TextView input = (TextView) findViewById(R.id.editText);

        serverConnectionThread = new ConnectionToServer(); // Instansierar async tråd klassen som används för att skicka och mottaga meddelanden.
        serverConnectionThread.execute(); // startar lyssnandet efter meddelanden

        // Lyssnare som anropar sendMsg i async thread klassen för att skicka meddelande till servern.
        buttonSendMsg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                serverConnectionThread.sendMsg(input.getText().toString());
                input.setText("");
            }
        });

    }


    /**
     * Location listener som tar emot kordinater och sparar dem för att sedan avregistrera sig från uppdateringar.
     */
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            locationManager.removeUpdates(this);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}
    };


    // Innre klass som körs i bakgrunden och hanterar anslutningen till chatservern.
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
            chatWindow.append("\n - " + progress[0]);
        }

        // Metod som sänder en string till servern, skickar även med username och location.
        protected void sendMsg(String msg){

            if(userLocation == null) { // Kollar ifall userLocation inte finns
                try {
                    Geocoder gcd = new Geocoder(UserChat.this, Locale.getDefault());
                    List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1); // hämtar location från kordinater
                    if (addresses.size() > 0) { // kollar att svar returnerades
                        userLocation = addresses.get(0).getLocality(); // sparar location
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Skriver meddelande med location och username
            out.println(username + " i " + userLocation + ": "  + msg);

        }

    }


}



