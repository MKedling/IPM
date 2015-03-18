package com.example.mattias.extraipm742;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

//Klass som skriver ut omgivande ljus från Enviroment sensor.

public class MainActivity extends Activity implements SensorEventListener{

    private TextView tw;
    private SensorManager sensorManager;
    private Sensor light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tw = (TextView) findViewById(R.id.text_view);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getSensorList(Sensor.TYPE_LIGHT).size() != 0){ // kollar att enheten stödjer light
            light = sensorManager.getSensorList(Sensor.TYPE_LIGHT).get(0); // Hämtar light sensorn
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL); // Registerar lyssnaren
        }else{
            tw.setText("Stöds ej");
        }
    }

    // Skirver ut ljus data då den ändras
    @Override
    public void onSensorChanged(SensorEvent event) {
        tw.setText("Omgivande ljus : " + event.values[0]);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // Registerar igen vid resume
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Avregistrerar vid pause
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
