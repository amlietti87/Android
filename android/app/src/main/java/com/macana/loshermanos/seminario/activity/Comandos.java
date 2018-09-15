package com.macana.loshermanos.seminario.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.macana.loshermanos.seminario.MainActivity;
import com.macana.loshermanos.seminario.R;
import com.macana.loshermanos.seminario.data.Activaciones;

import java.util.ArrayList;
import java.util.List;

public class Comandos extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ListView list_info;
    private ListView list_security;
    private ArrayList<Activaciones>Activaciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandos);

        // Recupero el numero de alarma seteado en las preferencias.
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        final String CelularDeAlarma = prefs.getString("CelularAlarma","12345");

        //Recupero el array de activaciones.
        loadArray();

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
        list_security.setAdapter(new MyListAdapter(this, R.layout.items_activaciones, Activaciones));


        list_info = (ListView) findViewById(R.id.list_info);
        final String[] info = new String[] {"Estado","Saldo"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
        list_info.setAdapter(adapter);


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

    // Armado del contenedor de la lista dinamica de activaciones.

    private class MyListAdapter extends ArrayAdapter<Activaciones>
    {
        private int layout;
        private MyListAdapter(Context context, int resource, List<Activaciones> objects){
            super(context, resource, objects);
            layout = resource;
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
            }

            Context context = getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            final String CelularDeAlarma = prefs.getString("CelularAlarma","12345");
            final viewHolder viewHolder = new viewHolder();
            Activaciones act = Activaciones.get(pos);
            viewHolder.id = (TextView)convertView.findViewById(R.id.id_hidden);
            viewHolder.id.setText(act.getActivacionesId());
            viewHolder.actnom = (TextView)convertView.findViewById(R.id.actnom);
            viewHolder.actnom.setText(act.getActivacionNom());
            viewHolder.btnact = (ImageButton)convertView.findViewById(R.id.btn_act);
            viewHolder.btnact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(String.valueOf(viewHolder.id.getText())) == 5){
                        SendSMS.SendSMS(getContext(),CelularDeAlarma,"reactivar");
                    }
                    else {
                        SendSMS.SendSMS(getContext(),CelularDeAlarma, "activar " + viewHolder.id.getText());
                    }

                }
            });
            viewHolder.btndsact = (ImageButton)convertView.findViewById(R.id.btn_dsact);
            viewHolder.btndsact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendSMS.SendSMS(getContext(), CelularDeAlarma, "desactivar");
                }
            });
            return convertView;
        }
    }

    public class viewHolder {
        TextView id;
        TextView actnom;
        ImageButton btnact;
        ImageButton btndsact;
    }

    public void loadArray(){
        SharedPreferences act = getApplicationContext().getSharedPreferences("Activacion", Context.MODE_PRIVATE);
        int size = act.getInt("Activacion_size", 0);
        String Id = "";
        String Nom = "";
        for (int i = 0; i < size; i++){
            Id = Integer.toString(i+1);
            Nom = act.getString("Activacion_"+i, null);
            Activaciones activaciones = new Activaciones (Id, Nom);
            Activaciones.add(activaciones);
            Id = "";
            Nom = "";
        }

    }
}
