package com.macana.loshermanos.seminario.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.macana.loshermanos.seminario.MainActivity;
import com.macana.loshermanos.seminario.R;

public class Comandos extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ListView list_info;
    private ListView list_security;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandos);

        // Recupero el numero de alarma seteado en las preferencias.
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        final String CelularDeAlarma = prefs.getString("CelularAlarma","12345");

        // Armado y completado de los tabs.
        TabHost tab = (TabHost) findViewById(R.id.TabHost);
        tab.setup();


        TabHost.TabSpec spec1 = tab.newTabSpec("Seguridad");
        spec1.setIndicator("Seguridad");
        spec1.setContent(R.id.layout1);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("Informacion");
        spec2.setIndicator("Informacion");
        spec2.setContent(R.id.layout2);
        tab.addTab(spec2);

        list_security = (ListView) findViewById(R.id.list_seguridad);
        final String[] seguridad = new String[] {"Activar","Desactivar","Reactivar"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seguridad);
        list_security.setAdapter(adapter);

        list_info = (ListView) findViewById(R.id.list_info);
        final String[] info = new String[] {"Estado","Saldo"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
        list_info.setAdapter(adapter);

        list_security.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0:
                        SendSMS.SendSMS(getApplicationContext(),CelularDeAlarma,seguridad[0].toString());
                        break;
                    case 1:
                        SendSMS.SendSMS(getApplicationContext(),CelularDeAlarma,seguridad[1].toString());
                        break;
                    case 2:
                        SendSMS.SendSMS(getApplicationContext(),CelularDeAlarma,seguridad[1].toString());
                        break;
                }
            }
        });

        list_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0:
                        SendSMS.SendSMS(getApplicationContext(),CelularDeAlarma,info[0].toString());
                        break;
                    case 1:
                        SendSMS.SendSMS(getApplicationContext(),CelularDeAlarma,info[1].toString());
                        break;
                }
            }
        });

        ImageButton btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actionIntent = new Intent(Comandos.this,
                        MainActivity.class);
                actionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(actionIntent);
            }
        });

    }
}
