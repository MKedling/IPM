package com.example.mattias.gesaell;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Game extends Activity {

    GameThread gameThread;
    Bitmap myBall;
    int xBall, yBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameThread = new GameThread(this);
        myBall = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        xBall = 250;
        yBall = 250;
        setContentView(gameThread);

    }

    @Override
    public void onPause() {
        super.onPause();
        gameThread.exit(); // avlsuta exekveringen
    }


    public class GameThread extends SurfaceView implements Runnable, SensorEventListener{

        SurfaceHolder surfaceHolder;
        Thread gameThread = null;
        private SensorManager sensorManager;
        private Sensor acceleration;
        private boolean booleanRun = true;
        int maxWidth, maxHeight;


        public GameThread(Context context){
            super(context);
            surfaceHolder = getHolder();

            gameThread = new Thread(this);
            sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
            if(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0){ // kollar att enheten stödjer accelerometer
                acceleration = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0); // Hämtar accelerator sensor
                sensorManager.registerListener(this, acceleration, SensorManager.SENSOR_DELAY_GAME); // Registerar lyssnaren
            }else {

            }

            gameThread.start();

        }

        @Override
        public void run() {

            Canvas canvas;
            boolean surfaceNotReady = true;
            while(surfaceNotReady) {
                if (!surfaceHolder.getSurface().isValid()) { // kolla om surface inte är tillgänglig
                    continue; // calla loopen igen, dvsa skippa resten av koden.
                }

                canvas = surfaceHolder.lockCanvas();
                // Sparar canvas höjd och bredd.
                maxWidth = canvas.getWidth();
                maxHeight = canvas.getHeight();
                surfaceNotReady = false;
                surfaceHolder.unlockCanvasAndPost(canvas);
            }


            while (booleanRun) {
                if (!surfaceHolder.getSurface().isValid()) { // kolla om surface inte är tillgänglig
                    continue; // calla loopen igen, dvsa skippa resten av koden.
                }

                canvas = surfaceHolder.lockCanvas(); // hämtar och låser canvas.
                canvas.drawARGB(150, 100, 200, 10); // Målar bakgrund
                canvas.drawBitmap(myBall, xBall, yBall, null); // Ritar ut spelarens bild.

                surfaceHolder.unlockCanvasAndPost(canvas);
            }

        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            if(event.sensor.getType()  == Sensor.TYPE_ACCELEROMETER) {
                //uppdatera X
                if (event.values[1] > 0) { // xBall är positiv
                    if (xBall > maxWidth - myBall.getWidth()) {
                        //ändra ej xBall för att få den att inte åka utanför
                    } else {
                        xBall += event.values[1] * 5;
                    }
                } else { // xBall är negativ
                    if (xBall < 0) {
                        //ändra ej xBall för att få den att inte åka utanför
                    } else {
                        xBall += event.values[1] * 5;
                    }
                }

                // Uppdatera Y
                if (event.values[0] > 0) { // yBall är positiv
                    if (yBall > maxHeight - myBall.getHeight()) {
                        // ändra ej yBall för att inte få den utanför
                    } else {
                        yBall += event.values[0] * 5;
                    }
                } else { // yBall är negativ
                    if (yBall < 0) {
                        // ändra ej yBall för att inte få den utanför
                    } else {
                        yBall += event.values[0] * 5;
                    }

                }
            }

        }


        protected void exit(){
            booleanRun = false;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }




}
