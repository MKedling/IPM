package com.example.mattias.ipm11;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by mattias on 2015-03-04.
 */
public class Tab2 extends Activity {

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        TextView tw = new TextView(this);
        tw.setText("Tab nummer 2");
        setContentView(tw);

    }

}
