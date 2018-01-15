package com.macana.loshermanos.seminario;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.macana.loshermanos.seminario.activity.Comandos;
import com.macana.loshermanos.seminario.activity.ListaSalidas;
import com.macana.loshermanos.seminario.activity.Preferencias;
import com.macana.loshermanos.seminario.activity.SendSMS;
import com.macana.loshermanos.seminario.service.ReceiverService;


public class MainActivity extends AppCompatActivity {
    private boolean flagCameFromSettings = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.e("mainactivity", "activity oncreate");

        //chequear permisos
        checkPermissions();

        //Iniciar Servicio
        startService(new Intent(this, ReceiverService.class));


        ImageButton btnsalidas = (ImageButton) findViewById(R.id.btn_sal);
        btnsalidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preferencesIntent = new Intent(MainActivity.this,
                        ListaSalidas.class);
                startActivity(preferencesIntent);
            }
        });

        ImageButton btncomandos = (ImageButton) findViewById(R.id.btn_com);
        btncomandos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comandosIntent = new Intent(MainActivity.this,
                        Comandos.class);
                startActivity(comandosIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();
        if (id == R.id.settings){
            Intent actionIntent = new Intent(MainActivity.this,
                    Preferencias.class);
            startActivity(actionIntent);
        }
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d("mainactivity", "activity ondestroy");
        //sendBroadcast(new Intent("RestartOurReceiverService"));//no hace falta
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("mainactivity", "activity on resume");
        if(flagCameFromSettings){
            Log.e("ERROR","CAME FROM SETTINGS");
            Intent actualintent = getIntent();
            actualintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(actualintent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int i = 0;
        boolean showDialog=false;
        for(String permission: permissions) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                Log.d("perms>onRqtPerms", permission + " onrequest: concedido!");
            } else {
                Log.d("perms>onRqtPerms", permission + " onrequest: NO concedido!");
                showDialog = true;
            }
            i++;
        }

        if (showDialog){
            openDialog();
        }
        else {
            //chequeo de numero seteado
            check_preferences();
        }
    }


    private void checkPermissions() {
        int Permission_All = 1;

        String[] Permissions = {Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS};

        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_2btns_greenred);
        //dialog.setTitle("");

        TextView txtView_Cuerpo = (TextView) dialog.findViewById(R.id.dialog_info);
        txtView_Cuerpo.setText(getResources().getString(R.string.app_name)+" requiere uno o más permisos para el correcto funcionamiento aunque están deshabilitados.\n¿Desea abrir preferencias de la aplicación en su celular?\nRecuerde permitir todos los permisos disponibles ");

        Button btnDialogOK = (Button) dialog.findViewById(R.id.dialog_green_btn);
        btnDialogOK.setText("Si, abrir preferencias");

        btnDialogOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flagCameFromSettings = true;
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        Button btnDialogCancel = (Button) dialog.findViewById(R.id.dialog_red_btn);
        btnDialogCancel.setText("No, cancelar.");
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //chequeo de numero seteado
                check_preferences();
            }
        });

        dialog.show();

    }
    public boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission: permissions){
                //Log.d("perms>hasPermissions",permission+" chequeando permiso");
                if(ActivityCompat.checkSelfPermission(context, permission)!=PackageManager.PERMISSION_GRANTED){
                    //Log.d("perms>hasPermissions",permission+" sin permiso para permiso:");
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission) ;
                    if (! showRationale) {
                        // usuario tildo "never ask again"
                        //Log.d("perms>hasPermissions",permission+" never task tildado");
                    } else  {
                        // usuario no presionó "never ask again"
                        //Log.d("perms>hasPermissions",permission+" permiso denegado");
                    }
                    return  false;
                } else {
                    Log.d("perms>hasPermissions",permission+" tiene permiso");
                    //chequeo de numero seteado
                    check_preferences();
                }
            }
        }
        return true;
    }

    private void check_preferences(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String CelularDeAlarma = prefs.getString("CelularAlarma","");
        if (!Preferencias.checkPhoneNumberprima(CelularDeAlarma)){
            Intent preferencesIntent = new Intent(MainActivity.this,
                    Preferencias.class);
            startActivity(preferencesIntent);
        }
    }


}

