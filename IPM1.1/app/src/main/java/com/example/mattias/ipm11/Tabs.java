package com.example.mattias.ipm11;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;


public class Tabs extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Tab Ett"); // Titel på tab
        tabHost.addTab(tabSpec);

        //Tab 2
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Tab Två"); // Titel på tab
        tabHost.addTab(tabSpec);

        //Tab 3
        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.tab3);
        tabSpec.setIndicator("Tab Tre"); // Titel på tab
        tabHost.addTab(tabSpec);

    }

}
