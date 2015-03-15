package com.example.mattias.extraipm22;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// Uppgift 2.2  hittar större primtal och sparar alla funna primtal i interna databasen
public class MainActivity extends Activity {

    private TextView tw;
    private LoopPrime backGroundThread = null;
    private Database myDB;
    private SQLiteDatabase writeDB, readDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonShow = (Button) findViewById(R.id.button);
        tw = (TextView) findViewById(R.id.textarea);

        final Button buttonSql = (Button) findViewById(R.id.button_sql);

        final String readQuery = "SELECT * FROM " + Database.TABLE_NAME; // Query för att få alla data från databasens tabell
        myDB = new Database(MainActivity.this);
        readDB = myDB.getReadableDatabase();
        writeDB = myDB.getWritableDatabase();


        buttonSql.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Cursor cur = readDB.rawQuery(readQuery, null);

                if(cur.getCount() == 0){ // Kollar att cursorn är tom, dvsa om det inte finns/returerades data från databasen
                    buttonSql.setText("Stoppa");
                    backGroundThread = (LoopPrime) new LoopPrime().execute(0L); // Starta exikveringen från 0.
                    tw.setText("Startad för första gången");
                }else { // Cursorn fick data
                    if (backGroundThread != null) { // kollar ifall det redan finns bakgrundstråd, dsva att exekvering pågår
                        backGroundThread.alive = false; // Stoppar exekveringen
                        backGroundThread = null;
                        tw.setText("");
                        cur = readDB.rawQuery(readQuery, null);
                        display(cur); //Lista senaster primes
                        buttonSql.setText("Starta");
                    } else { // Ingen exekvering pågår, starta exekvering
                        buttonSql.setText("Stoppa");
                        tw.setText("");
                        Long last = display(cur); //Visa senaste primes och få det senaste
                        backGroundThread = (LoopPrime) new LoopPrime().execute(last); // Starta exikveringen från det senaste funna.
                    }
                }
            }
        });

        // Lyssnarmetod för att lista senaste funna primes
        buttonShow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor cur = readDB.rawQuery(readQuery, null);
                if(cur.getCount() == 0){
                    tw.setText("DB tom, starta först!");
                }else {
                    tw.setText("");
                    display(cur);
                }
            }
        });

    }

       // Metod som listar de senaste primsen, returnerar det senaste funna primet.
    private Long display(Cursor cur){

        int curr = 0;
        cur.moveToLast();
        Long last = cur.getLong(0);
        tw.append("Senaste\n" + last + "  : " + cur.getString(1) + "\nTidigare\n");
        while (cur.moveToPrevious() && curr < 10) {
            tw.append(cur.getLong(0) + "  : " + cur.getString(1) + "\n");
            curr++;
        }

        return last;
    }

    // Metod som läggertill en long i Databasen
    private void insertDB(Long i){
        //Insert
        ContentValues cVal = new ContentValues();
        cVal.put(Database.FIRST_COL, i);
        long id = writeDB.insert(Database.TABLE_NAME, null, cVal);
    }

    //Innre klass som kan köras som Async thread, leter efter nya primes
    private class LoopPrime extends AsyncTask<Long, Void, Void> {

        protected boolean alive = true;
        @Override
        protected Void doInBackground(Long... params) {
            long lng = params[0];
            //Execute metod som letar efter nya primes och lägger till alla som hittas.
            while(alive) {
                lng +=2;
                if (isPrime(lng)) {
                    insertDB(lng);
                }
            }
            return null;
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
