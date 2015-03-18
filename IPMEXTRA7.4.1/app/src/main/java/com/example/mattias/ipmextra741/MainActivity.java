package com.example.mattias.ipmextra741;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

// Klass som sparar och skriver ut data från accelerator och linear accelerator Sensorer
public class MainActivity extends Activity implements SensorEventListener{

    TextView tw;
    private float accelerometerX, accelerometerY, accelerometerZ, linearAccelerationX,linearAccelerationY, linearAccelerationZ;
    private Sensor linearAcceleration, accelerator;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tw = (TextView) findViewById(R.id.text_view);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0 && sensorManager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION).size() != 0){ // kollar att enheten stödjer accelerometer
            accelerator = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0); // Hämtar accelerometer sensorn
            linearAcceleration = sensorManager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION).get(0); // Hämtar linear accelerator sensorn
            sensorManager.registerListener(this, accelerator, SensorManager.SENSOR_DELAY_NORMAL); // Registerar lyssnaren
            sensorManager.registerListener(this, linearAcceleration, SensorManager.SENSOR_DELAY_NORMAL); // Registerar lyssnaren
        }else {
            tw.setText("Enheter stödjer inte någon sensor");
        }
    }

    // Sparar och skriver ut x y z värdena då de ändras.
    @Override
    public void onSensorChanged(SensorEvent event) {
        // kollar vilken sensor som ändrats.
        switch(event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:
                accelerometerX = event.values[0];
                accelerometerY = event.values[1];
                accelerometerZ = event.values[2];
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                linearAccelerationX = event.values[0];
                linearAccelerationY = event.values[1];
                linearAccelerationZ = event.values[2];
                break;
        }

        tw.setText("Accelerometer \n" +  "x: " + accelerometerX + "\ny: " + accelerometerY + "\nz: " +accelerometerZ
                    + "\n\n" + "Linear Acceleration \n" + "x: " + linearAccelerationX + "\ny: " + linearAccelerationY
                    + "\nz: " + linearAccelerationZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // Registerar vid resume
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerator, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, linearAcceleration, SensorManager.SENSOR_DELAY_NORMAL);
    }
    // Avregistrerar vid pause
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}
