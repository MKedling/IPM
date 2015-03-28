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
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;


/**
 * Klass som implementerar spelet
 */
public class Game extends Activity {

    GameThread gameThread;
    Bitmap myBall;
    int xBall, yBall;
    Bitmap myStar;
    int xStar, yStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameThread = new GameThread(this);
        myBall = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship); // Instantsierar bitmapen som representerar spelaren.
        myStar = BitmapFactory.decodeResource(getResources(), R.drawable.star); // Instantsierar bitmapen som representerar stjärnan.
        xBall = 250;
        yBall = 250;
        setContentView(gameThread);

    }

    @Override
    public void onPause() {
        super.onPause();
        gameThread.exit(); // avlsuta exekveringen
    }


    /**
     * Innre klass som är spelmotorn.
     */
    public class GameThread extends SurfaceView implements Runnable, SensorEventListener{

        private SurfaceHolder surfaceHolder;
        private Thread gameThread = null;
        private SensorManager sensorManager;
        private Sensor acceleration;
        private boolean booleanRun = true;
        private int maxWidth, maxHeight;
        private Random rand;

        //Konstruktor
        public GameThread(Context context){
            super(context);
            surfaceHolder = getHolder();
            rand = new Random();

            gameThread = new Thread(this);

            sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
            if(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0){ // kollar att enheten stödjer accelerometer
                acceleration = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0); // Hämtar accelerator sensor
                sensorManager.registerListener(this, acceleration, SensorManager.SENSOR_DELAY_FASTEST); // Registerar lyssnaren
            }else {
                //Enheter stödjer inte, gör något vettigt
            }

            gameThread.start();

        }

        // Run metod som är spelmotorn
        @Override
        public void run() {

            Canvas canvas;
            boolean surfaceNotReady = true;

            while(surfaceNotReady) {// Väntar på surface ready och sätter höjd och bredd för spelplanen
                if (!surfaceHolder.getSurface().isValid()) { // kolla om surface inte är tillgänglig
                    continue; // calla loopen igen, dvsa skippa resten av koden.
                }

                canvas = surfaceHolder.lockCanvas();
                // Sparar canvas höjd och bredd.
                maxWidth = canvas.getWidth();
                maxHeight = canvas.getHeight();

                Log.d("Max", maxHeight + "x" + maxWidth);

                surfaceNotReady = false; // avslutar while loopen
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            newStarCoordinates(); // skapar nya koordinater för stjärnan

            while (booleanRun) {
                if (!surfaceHolder.getSurface().isValid()) { // kolla om surface inte är tillgänglig
                    continue; // calla loopen igen, dvsa skippa resten av koden.
                }

                //Kollar ifall rektanglarna överlappar varandra.
                if(xBall + myBall.getWidth() > xStar && xBall < xStar + myStar.getWidth() &&
                   yBall + myBall.getHeight() >yStar && yBall < yStar + myStar.getHeight()){

                    newStarCoordinates();// skapar nya koordinater för stjärnan

                }

                canvas = surfaceHolder.lockCanvas(); // hämtar och låser canvas.
                canvas.drawRGB(25, 25, 25); // Målar svart  bakgrund
                canvas.drawBitmap(myBall, xBall, yBall, null); // Ritar ut spelarens bild.
                canvas.drawBitmap(myStar, xStar, yStar, null); // Ritar ut stjärnan

                surfaceHolder.unlockCanvasAndPost(canvas); // låser up och visar canvas.

            }

        }

        // metod som lyssnar efter ändringar från acceleratorn och uppdaterar hur ryndskeppet skall åka.
        @Override
        public void onSensorChanged(SensorEvent event) {

            if(event.sensor.getType()  == Sensor.TYPE_ACCELEROMETER) { // Kollar ifall det är TYPE_ACCELEROMETER som ändrats
                //uppdatera X
                if (event.values[1] > 0) { // xBall är positiv
                    if (xBall > maxWidth - myBall.getWidth()) {
                        //ändra ej xBall för att få den att inte åka utanför
                    } else {
                        xBall += event.values[1];
                    }
                } else { // xBall är negativ
                    if (xBall < 0) {
                        //ändra ej xBall för att få den att inte åka utanför
                    } else {
                        xBall += event.values[1];
                    }
                }

                // Uppdatera Y
                if (event.values[0] > 0) { // yBall är positiv
                    if (yBall > maxHeight - myBall.getHeight()) {
                        // ändra ej yBall för att inte få den utanför
                    } else {
                        yBall += event.values[0];
                    }
                } else { // yBall är negativ
                    if (yBall < 0) {
                        // ändra ej yBall för att inte få den utanför
                    } else {
                        yBall += event.values[0];
                    }

                }
            }

        }

        /**
         * Metod för att byta kordinater där stjärnan skall visas.
         */
        private void newStarCoordinates(){

            xStar = rand.nextInt(maxWidth - myStar.getWidth());
            yStar = rand.nextInt(maxHeight - myStar.getHeight());

            Log.d("Star: ", xStar + "x" + yStar  );
        }

        /**
         * Avlsutar exekveringen
         */
        protected void exit(){
            booleanRun = false; // avsluta run loopen
            sensorManager.unregisterListener(this); // Lyssna ej efter uppdateringar
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}


    }



}
