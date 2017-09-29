package com.example.seminario2.primeraapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.seminario2.primeraapp.data.ControlBoxContract.CelularesEntry;
import com.example.seminario2.primeraapp.data.ControlBoxContract.SalidasEntry;
import com.example.seminario2.primeraapp.data.ControlBoxContract.TagsEntry;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "controlbox.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CELULARES_CREATE =
            "CREATE TABLE " + CelularesEntry.TABLE_NAME + " (" +
                    CelularesEntry._ID + " INTEGER PRIMARY KEY, " +
                    CelularesEntry.COLUMN_CELULARES + " TEXT " +
                    ")";

    private static final String TABLE_SALIDAS_CREATE =
            "CREATE TABLE " + SalidasEntry.TABLE_NAME + " (" +
                    SalidasEntry._ID + " INTEGER PRIMARY KEY, " +
                    SalidasEntry.COLUMN_SALIDAS + " TEXT " +
                    ")";

    private static final String TABLE_TAGS_CREATE =
            "CREATE TABLE " + TagsEntry.TABLE_NAME + " (" +
                    TagsEntry._ID + " INTEGER PRIMARY KEY, " +
                    TagsEntry.COLUMN_TAGS + " TEXT " +
                    ")";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CELULARES_CREATE);
        db.execSQL(TABLE_TAGS_CREATE);
        db.execSQL(TABLE_SALIDAS_CREATE);
        for (int i = 0; i < 8; i++) {
            String query = "INSERT INTO salidas ("
                    + ControlBoxContract.SalidasEntry.COLUMN_SALIDAS + ")"
                    + "VALUES (\"Salida: "  + (i+1) + "\")";
            db.execSQL(query);
        }

        //seed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SalidasEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CelularesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TagsEntry.TABLE_NAME);
        onCreate(db);
    }


}
