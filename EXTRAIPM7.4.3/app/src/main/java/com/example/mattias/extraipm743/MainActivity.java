package com.example.mattias.extraipm743;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

// Klass som skriver ut data från magnetic field sensorn då den ändras.
public class MainActivity extends Activity implements SensorEventListener{

    TextView tw;
    private Sensor sensorMagneticField;
    private SensorManager sensorManager;
    private float magneticX, magneticY, magneticZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tw = (TextView) findViewById(R.id.text_view);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).size() != 0){ // kollar att enheten stödjer magnetic field
            sensorMagneticField = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0); // Hämtar magnetic field sensorn
            sensorManager.registerListener(this, sensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL); // Registerar lyssnaren
        }else {
            tw.setText("Enheter stödjer inte någon sensor");
        }
    }

    // Sparar och skriver ut x y z värdena då de ändras.
    @Override
    public void onSensorChanged(SensorEvent event) {
        // kollar vilken sensor som ändrats.
        switch(event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                magneticX = event.values[0];
                magneticY = event.values[1];
                magneticZ = event.values[2];
                break;
        }
        tw.setText("Magnetic\n" + "x: " + magneticX + "\ny: " + magneticY + "\nz: " + magneticZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // Registerar vid resume
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL);

    }
    // Avregistrerar vid pause
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}
