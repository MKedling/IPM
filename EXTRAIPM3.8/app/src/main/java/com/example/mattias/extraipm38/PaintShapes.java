package com.example.mattias.extraipm38;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;

import android.view.View;

// Klass som skapar och ritar ut 3 drawables
public class PaintShapes extends View {

    private ShapeDrawable drawable1, drawable2, drawable3;

    //Ints för att ange storlek och position.
    private int x1 = 100;
    private int y1 = 100;
    private int width1 = 150;
    private int height1 = 150;

    private int x2 = 100;
    private int y2 = 450;
    private int width2 = 200;
    private int height2 = 200;

    private int x3 = 400;
    private int y3 = 250;
    private int width3 = 200;
    private int height3 = 300;


    // konstruktor som skapar 3 st drawableobjekt
    public PaintShapes(Context context) {
        super(context);

        //Skapar drawable objekt, sätter färg, storlek och position.
        drawable1 = new ShapeDrawable(new OvalShape());
        drawable1.getPaint().setColor(Color.RED);
        drawable1.setBounds(x1, y1, x1 + width1, y1 + height1);

        drawable2 = new ShapeDrawable(new RectShape());
        drawable2.getPaint().setColor(Color.BLUE);
        drawable2.setBounds(x2, y2, x2 + width2, y2 + height2);

        drawable3 = new ShapeDrawable(new OvalShape());
        drawable3.getPaint().setColor(Color.GREEN);
        drawable3.setBounds(x3, y3, x3 + width3, y3 + height3);

    }

    // onDraw metod som ritar ut de 3 objekten.
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        drawable1.draw(canvas);
        drawable2.draw(canvas);
        drawable3.draw(canvas);


    }



}