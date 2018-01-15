package com.macana.loshermanos.seminario.activity;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.macana.loshermanos.seminario.data.DBHelper;

/**
 * Created by andreslietti on 10/5/17.
 */

public class ApplicationClass extends Application {


    private DBHelper helper;

    @Override
    public void onCreate() {
        super.onCreate();
        helper = new DBHelper(getApplicationContext());
    }

    public SQLiteDatabase getReadableDB(){
        return helper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDB(){
        return helper.getWritableDatabase();
    }
}
