package com.example.mattias.ipm21;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// Uppgift 2.1  hittar större primtal och sparar det sista funna primtalet i shared preferences
public class MainActivity extends Activity {

    private TextView tw;
    private LoopPrime backGroundThread = null;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        tw = (TextView) findViewById(R.id.textarea);

        //Button listener som startar/stoppar prime loopandet
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(settings == null){
                    settings = getSharedPreferences("saved pre", 0);
                }
                if(backGroundThread != null){
                    backGroundThread.alive = false;
                    backGroundThread = null;
                }else {
                    backGroundThread = (LoopPrime) new LoopPrime().execute(settings.getLong("saved big prime", 1));
                }
            }
        });
    }

    //Innre klass som kan köras som Async thread
    private class LoopPrime extends AsyncTask<Long, Long, Long> {

        protected boolean alive = true;
        @Override
        protected Long doInBackground(Long... params) {
            long lng = params[0];

            // få referens till editor
            SharedPreferences.Editor editor = settings.edit();

            while(alive) {
                lng +=2;
                if (isPrime(lng)) {
                    //Uppdatera UI
                    publishProgress(lng);
                    //Lägg in senaste värde
                    editor.putLong("saved big prime", lng);
                    // lägg till ändringar
                    editor.commit();
                }
            }
            return lng;
        }

        //Metod som körs i UI tråden som uppdaterar textviewn.
        @Override
        protected void onProgressUpdate(Long... result) {
            tw.setText("Primtal: " + result[0]);
        }

        //Letar efter primtal, kod från uppgiftsbeskrivningen
        private boolean isPrime(long candidate) {
            long sqrt = (long)Math.sqrt(candidate);
            for(long i = 3; i <= sqrt; i += 2)
                if(candidate % i == 0) return false;
            return true;
        }

    }



}
