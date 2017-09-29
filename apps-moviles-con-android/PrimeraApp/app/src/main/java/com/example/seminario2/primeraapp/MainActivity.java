package com.example.seminario2.primeraapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seminario2.primeraapp.data.ControlBoxContract;
import com.example.seminario2.primeraapp.data.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper helper = new DataBaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        //CreateSalidas();
        Button btnchange = (Button) findViewById(R.id.button);
        btnchange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(MainActivity.this,
                        TestActivity.class);
                startActivity(testIntent);
            }
        });
    }

}
