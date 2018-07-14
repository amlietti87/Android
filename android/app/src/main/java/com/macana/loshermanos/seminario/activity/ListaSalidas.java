package com.macana.loshermanos.seminario.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.macana.loshermanos.seminario.MainActivity;
import com.macana.loshermanos.seminario.R;
import com.macana.loshermanos.seminario.data.CBContract;
import com.macana.loshermanos.seminario.data.DBHelper;
import com.macana.loshermanos.seminario.data.Salidas;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListaSalidas extends AppCompatActivity {
    ArrayList<Salidas> SalidasNombre = new ArrayList<>();
    ArrayAdapter<Salidas> salidasArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_salidas);
        ListView listView = (ListView) findViewById(R.id.lvsalidas);
        readData();
        listView.setAdapter(new MyListAdapter(this, R.layout.items_salidas, SalidasNombre));
        ImageButton btnActSal = (ImageButton) findViewById(R.id.btn_actualizar);
        ImageButton btnHome = (ImageButton) findViewById(R.id.btn_home);

        // Boton actualizar salidas. Envia SMS pidiendo las salidas habilitadas.
        btnActSal.setOnClickListener(new View.OnClickListener() {
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            final String CelularDeAlarma = prefs.getString("CelularAlarma","12345");
            @Override
            public void onClick(View view) {
                SendSMS.SendSMS(getApplicationContext(),CelularDeAlarma,"Salidas");
            }
        });

        // Boton Home, vuelve al main activity.
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent preferencesIntent = new Intent(ListaSalidas.this,
                        MainActivity.class);
                preferencesIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(preferencesIntent);
            }
        });
    }



    // Armado del contenedor de la lista dinamica de salidas.
    private class MyListAdapter extends ArrayAdapter<Salidas>{
        private int layout;
        private MyListAdapter(Context context, int resource, List<Salidas> objects) {
            super(context, resource, objects);
            layout = resource;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
            }
            Context context = getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            final String CelularDeAlarma = prefs.getString("CelularAlarma","12345");
            final viewHolder viewHolder = new viewHolder();
            Salidas sal = SalidasNombre.get(position);
            viewHolder.id = (TextView)convertView.findViewById(R.id.hidden);
            viewHolder.id.setText(sal.getSalidasId());
            viewHolder.salida = (TextView) convertView.findViewById(R.id.salnom);
            viewHolder.salida.setText(sal.getSalidasNombre());
            viewHolder.btnOn = (ImageButton) convertView.findViewById(R.id.btnon);
            viewHolder.btnOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getContext(), "on S0" + viewHolder.id.getText(), Toast.LENGTH_SHORT).show();
                    SendSMS.SendSMS(getContext(),CelularDeAlarma, "on S0"+viewHolder.id.getText());
                }
            });
            viewHolder.btnOff = (ImageButton) convertView.findViewById(R.id.btnof);
            viewHolder.btnOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getContext(), "of S0" + viewHolder.id.getText(), Toast.LENGTH_SHORT).show();
                    SendSMS.SendSMS(getContext(),CelularDeAlarma, "of S0"+viewHolder.id.getText());
                }
            });
            return convertView;
        }
    }

    public class viewHolder {
        TextView id;
        TextView salida;
        ImageButton btnOn;
        ImageButton btnOff;
    }

    // Buscar datos en la BD, armar un objeto salidas para luego mostrarlo en la lista.
    public void readData(){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {CBContract.SalidasEntry._ID,CBContract.SalidasEntry.COLUMN_HABILITADA,CBContract.SalidasEntry.COLUMN_NEMONICO,CBContract.SalidasEntry.COLUMN_NOMBRE,CBContract.SalidasEntry.COLUMN_TIEMPO};
        Cursor c = db.query(CBContract.SalidasEntry.TABLE_NAME, projection, null, null,null,null,null);
        int i = c.getCount();
        Log.d("Cantidad de columnas", String.valueOf(i));
        String salidaid ="";
        String salidahab ="";
        String salidanem ="";
        String salidanom = "";
        String salidatiem = "";

        while (c.moveToNext()){

            if (c.getString(1).equals("S")){
                salidaid = c.getString(0);
                salidahab = c.getString(1);
                salidanem = c.getString(2);
                salidanom = c.getString(3);
                salidatiem = c.getString(4);
                Salidas salida = new Salidas(salidaid,salidahab,salidanem,salidanom,salidatiem);
                SalidasNombre.add(salida);
                salidaid ="";
                salidahab = "";
                salidanem ="";
                salidanom = "";
                salidatiem = "";
            }

        }
        c.close();

    }
}
