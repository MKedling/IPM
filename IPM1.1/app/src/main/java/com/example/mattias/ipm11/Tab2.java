package com.example.mattias.ipm11;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Klass som skapar en aktivitet med olika komponeter för att träna på de olika komponenterna.
 * @author mattias kedling
 */
public class Tab2 extends Activity {

    private ListView carList;
    private String[] cars = new String[] {"Bmw", "Audi", "Volvo", "Mercedes", "Volkswagen", "Ferrari"};
    private ListView list;
    private GridView carsGrid;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2_layout);

        list = (ListView) findViewById(R.id.cars_list);
        carsGrid = (GridView) findViewById(R.id.gridView_cars);
        Button start = (Button)findViewById(R.id.start_progress);

        //Sätter en lyssnare till knappen
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startProgressBar();
            }
        });

        populateCarsList();
        populateGridView();
        registerListClicks(list);
        registerListClicks(carsGrid);

    }

    //Metod för att lägga till alla element i grid viewn.
    private void populateGridView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.car_list_layout, cars);
        carsGrid.setAdapter(adapter);
    }

    //Metod för att lägga till alla element i listan.
    private void populateCarsList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.car_list_layout, cars);
        list.setAdapter(adapter);
    }

    //Metod för att hantera klicks av items i listan.
    //Visar en popup med text från det item användaren klickade.
    private void registerListClicks(AbsListView obj) {
          obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  TextView textView = (TextView) view;
                  String text = textView.getText().toString();
                  Toast.makeText(Tab2.this, text, Toast.LENGTH_SHORT).show();
              }
          });

    }

    // Metod för att simulera en bakgrundsprocess
    // Startar en tråd som sover och sedan uppdaterar progress.
    private void startProgressBar(){

        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressStatus = 0; // Nollställer progress så den kan köras flera gånger.
        // Simulerar en download eller liknande
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    try {
                        Thread.sleep(25);
                    }catch (Exception e){
                    }
                    mProgressStatus++;

                    // Uppdarerar progressbar'n
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();

    }

}
