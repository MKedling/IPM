package com.example.mattias.extraipm22;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "PrimeDatabase";
    static final String TABLE_NAME = "PrimeTable";
    static final String FIRST_COL = "PrimeNumber";
    static final String SECOND_COL = "Date";
    static final int DB_VERSION = 1;
    Context context;


    //Sträng query för att skapa databasen
    private final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                              FIRST_COL + " LONG, " +
                              SECOND_COL + " DEFAULT CURRENT_TIMESTAMP);";

    Database(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        this.context = context;
    }

    // Callas första gången en DB skapas, här skapas allting.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Skapar tabellen från queryn.
        db.execSQL(TABLE_CREATE);
        System.out.println("DB CREATED !!!");
    }

    //Callas då någonting updateras, tex Drop tables eller add tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}