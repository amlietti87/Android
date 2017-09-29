package com.example.seminario2.primeraapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.seminario2.primeraapp.data.ControlBoxContract;
import com.example.seminario2.primeraapp.data.DataBaseHelper;

public class TestActivity extends AppCompatActivity {

    String salidas = "S01 Luces de Acceso,S02 Luces LedInterior,S03 Salida 03,S04 Salida 04,S05 Salida 05,S06 Salida 06,S07 BIP Lector RFID, S08 Sirena Int y Ext";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        readData();
        String [] Salidas = salidas.split(",");
        for (int i = 0; i < Salidas.length; i++) {
            int a = i + 1;
            updateData(a, Salidas[i]);
        }
        readData();
        Button btnchange = (Button) findViewById(R.id.button2);
        btnchange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(TestActivity.this,
                        MainActivity.class);
                startActivity(testIntent);
            }
        });
    }

    public void readData(){
        DataBaseHelper helper = new DataBaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {ControlBoxContract.SalidasEntry.COLUMN_SALIDAS};
        Cursor c = db.query(ControlBoxContract.SalidasEntry.TABLE_NAME, projection, null, null,null,null,null);
        int i = c.getCount();
        Log.d("Cantidad de columnas", String.valueOf(i));
        Toast.makeText(getApplicationContext(), "Numero de columnas " + i,Toast.LENGTH_SHORT).show();
        String rowContent = "";
        while (c.moveToNext()){
            rowContent = c.getString(0);
            Log.i("Row " + String.valueOf(c.getPosition()), rowContent);
            Toast.makeText(getApplicationContext(), rowContent,Toast.LENGTH_SHORT).show();
            rowContent = "";
        }
        c.close();
    }

    public void updateData(int Id, String NomSalidas){
        int id = Id;
        DataBaseHelper helper = new DataBaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] args = {String.valueOf(id)};
        ContentValues values = new ContentValues();
        values.put(ControlBoxContract.SalidasEntry.COLUMN_SALIDAS, NomSalidas);
        int numRows = db.update(ControlBoxContract.SalidasEntry.TABLE_NAME, values, ControlBoxContract.SalidasEntry._ID+" =?", args);
        Log.d("Update Rows", String.valueOf(numRows));
        db.close();
    }
}
