package com.macana.loshermanos.seminario.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.macana.loshermanos.seminario.data.CBContract.CelularesEntry;
import com.macana.loshermanos.seminario.data.CBContract.SalidasEntry;
import com.macana.loshermanos.seminario.data.CBContract.TagsEntry;

/**
 * Created by andreslietti on 10/3/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    // Construccion de la Base de Datos y las tablas de la misma.
    private static final String DATABASE_NAME = "controlbox.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CELULARES_CREATE =
            "CREATE TABLE " + CelularesEntry.TABLE_NAME + " (" +
                    CelularesEntry._ID + " INTEGER PRIMARY KEY, " +
                    CelularesEntry.COLUMN_CELNOM + " TEXT, " +
                    CelularesEntry.COLUMN_CELNUM + " TEXT, " +
                    CelularesEntry.COLUMN_CELCAT + " TEXT " +
                    ")";

    private static final String TABLE_SALIDAS_CREATE =
            "CREATE TABLE " + SalidasEntry.TABLE_NAME + " (" +
                    SalidasEntry._ID + " INTEGER PRIMARY KEY, " +
                    SalidasEntry.COLUMN_HABILITADA + " TEXT, " +
                    SalidasEntry.COLUMN_NEMONICO + " TEXT, " +
                    SalidasEntry.COLUMN_NOMBRE + " TEXT, " +
                    SalidasEntry.COLUMN_TIEMPO + " TEXT " +
                    ")";

    private static final String TABLE_TAGS_CREATE =
            "CREATE TABLE " + TagsEntry.TABLE_NAME + " (" +
                    TagsEntry._ID + " INTEGER PRIMARY KEY, " +
                    TagsEntry.COLUMN_TAGNOM + " TEXT, " +
                    TagsEntry.COLUMN_TAGNUM + " TEXT, " +
                    TagsEntry.COLUMN_TAGCAT + " TEXT " +
                    ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Se completan las base de datos con valores por defecto.
        db.execSQL(TABLE_CELULARES_CREATE);
        db.execSQL(TABLE_TAGS_CREATE);
        db.execSQL(TABLE_SALIDAS_CREATE);
        for (int i = 0; i < 8; i++) {
            String query = "INSERT INTO salidas ("
                    + SalidasEntry.COLUMN_NOMBRE + ","
                    + SalidasEntry.COLUMN_HABILITADA + ")"
                    + "VALUES (\"Salida: "  + (i+1) + "\", \"N\")";
            db.execSQL(query);
        }
        for (int i = 0; i < 10; i++) {
            String query = "INSERT INTO celulares ("
                    + CelularesEntry.COLUMN_CELNOM + ")"
                    + "VALUES (\"Celular: "  + (i+1) + "\")";
            db.execSQL(query);
        }
        for (int i = 0; i < 20; i++) {
            String query = "INSERT INTO tags ("
                    + TagsEntry.COLUMN_TAGNOM + ")"
                    + "VALUES (\"Tag: "  + (i+1) + "\")";
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
