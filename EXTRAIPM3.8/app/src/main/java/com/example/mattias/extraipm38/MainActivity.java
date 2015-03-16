package com.example.mattias.extraipm38;


import android.app.Activity;
import android.os.Bundle;

// Klass som skapar och visar ett PaintShapes object
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PaintShapes pr = new PaintShapes(this);
        setContentView(pr);

    }
}
