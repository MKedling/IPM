package com.example.mattias.extraipm51;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

// Klass som
public class MainActivity extends Activity {

    LocationManager locationManager;
    TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hämta location manager från system
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        tw = (TextView)findViewById(R.id.text_view);

        // Registrera lyssnare för att få uppdateringar när positionen ändrats.
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    // Lyssnare som skall anropas då gps location ändras
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //Skriver ut lite info om Location
            tw.append("\nLat: " + location.getLatitude() + "\nLong: " + location.getLongitude()
                       + "\nAcc: " +location.getAccuracy() + "\n Speed: " +location.getSpeed() );

            

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };



}
