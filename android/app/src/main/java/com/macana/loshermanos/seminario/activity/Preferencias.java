package com.macana.loshermanos.seminario.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.macana.loshermanos.seminario.MainActivity;
import com.macana.loshermanos.seminario.R;

public class Preferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        ImageButton btnActSal = (ImageButton) findViewById(R.id.btn_actualizar);
        ImageButton btnActAct = (ImageButton) findViewById(R.id.btn_activaciones);

        // Declaracion de preferencia.
        final SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        /* Seteo editText */
        final EditText EditTextCelularAlarma = (EditText)findViewById(R.id.editTextAlarmNumber);

        // Carga de numero de telefono a la preferencia.
        String CelularDeAlarma = prefs.getString("CelularAlarma","");
        EditTextCelularAlarma.setText(CelularDeAlarma);

        // Controlar largo del numero de telefono.
        checkPhoneNumber(EditTextCelularAlarma.getText().toString());

        // Guardar preferencia.
        ImageButton btnSavePreferences = (ImageButton) findViewById(R.id.btn_save);
        btnSavePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EditTextCelularAlarma = (EditText) findViewById(R.id.editTextAlarmNumber);

                String numeroAsetear = String.valueOf(EditTextCelularAlarma.getText().toString());
                checkPhoneNumber(numeroAsetear);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("CelularAlarma", numeroAsetear);
                editor.apply();
                Toast.makeText(getApplication().getApplicationContext(), "Guardando Preferencias "+numeroAsetear, Toast.LENGTH_SHORT).show();
            }
        });

        // Actualizar las Salidas.
        btnActSal.setOnClickListener(new View.OnClickListener() {
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            final String CelularDeAlarma = prefs.getString("CelularAlarma","12345");
            @Override
            public void onClick(View view) {
                SendSMS.SendSMS(getApplicationContext(),CelularDeAlarma,"Salidas");
            }
        });

        // Actualizar las Activaciones.
        btnActAct.setOnClickListener(new View.OnClickListener() {
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            final String CelularDeAlarma = prefs.getString("CelularAlarma","12345");
            @Override
            public void onClick(View view) {
                SendSMS.SendSMS(getApplicationContext(),CelularDeAlarma,"alarmas");
            }
        });

        // Volver al activity main.
        ImageButton btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent preferencesIntent = new Intent(Preferencias.this,
                        MainActivity.class);
                preferencesIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(preferencesIntent);
            }
        });

    }

    public static boolean checkPhoneNumberprima(String NumeroIngresado) {
        if (NumeroIngresado.length() <= 7 || NumeroIngresado.length() > 13 || !PhoneNumberUtils.isGlobalPhoneNumber(NumeroIngresado))
        {
            return false;
        }
        else
        {
            return true;

        }
    }

    private void checkPhoneNumber(String NumeroIngresado) {
        if (!checkPhoneNumberprima(NumeroIngresado))
        {
            Toast.makeText(getApplication().getApplicationContext(), "ERROR: el numero "+ NumeroIngresado +"no es correcto. Debe ser mayor a 7 caracteres y menor a 13", Toast.LENGTH_LONG).show();
            openDialog();

        }
        else
        {
            Toast.makeText(getApplication().getApplicationContext(), "OK: validacion OK del numero "+ NumeroIngresado, Toast.LENGTH_LONG).show();

            // validation successful
            //add plus? PhoneNo = "+";
            //PhoneNo = PhoneNo.concat(PhoneDigits); // adding the plus sign
        }
    }
    public void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_1btn_yellow);
        //dialog.setTitle("");

        TextView txtView_Cuerpo = (TextView) dialog.findViewById(R.id.dialog_info);
        txtView_Cuerpo.setText("El número telefónico ingresado debe tener entre 7 y 13 dígitos y puede comenzar con + si prefiere.\nIngresar un número incorrecto puede provocar que "+getResources().getString(R.string.app_name)+" no funcione correctamente.");

        Button btnDialogWarning = (Button) dialog.findViewById(R.id.dialog_yellow_btn);
        btnDialogWarning.setText("Continuar");

        btnDialogWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
