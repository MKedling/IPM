package com.example.mattias.ipm11;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;


public class MainActivity extends TabActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        //Skapa och lägg till Tab 1
        intent = new Intent(this, Tab1.class);
        spec = tabHost.newTabSpec("First").setIndicator("First").setContent(intent);
        tabHost.addTab(spec);

        //Skapa och lägg till Tab 2
        intent = new Intent(this, Tab2.class);
        spec = tabHost.newTabSpec("Second").setIndicator("Second").setContent(intent);
        tabHost.addTab(spec);



    }



}
