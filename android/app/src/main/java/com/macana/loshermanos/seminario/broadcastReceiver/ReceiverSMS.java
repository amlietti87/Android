package com.macana.loshermanos.seminario.broadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.macana.loshermanos.seminario.activity.ApplicationClass;
import com.macana.loshermanos.seminario.activity.ListaSalidas;
import com.macana.loshermanos.seminario.data.CBContract;
import com.macana.loshermanos.seminario.data.DBHelper;

import java.util.ArrayList;

/**
 * Created by andreslietti on 9/16/17.
 */

public class ReceiverSMS extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 0;
    final SmsManager sms = SmsManager.getDefault();
    private Context context;




    public ReceiverSMS() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        ApplicationClass app = (ApplicationClass) context.getApplicationContext();
        SQLiteDatabase db = app.getReadableDB();
        String Mensajes = null;
        String Remitente = null;
        ArrayList<String> Activaciones = new ArrayList<>();
        // Declaracion de preferencia.
        final SharedPreferences act = context.getSharedPreferences("Activacion", Context.MODE_PRIVATE);

        final Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            final Object[] sms = (Object[]) bundle.get("pdus");
            for (int i = 0; i < sms.length; i++) {
                SmsMessage MENSAJE = SmsMessage.createFromPdu((byte[]) sms[i]);
                Remitente = MENSAJE.getDisplayOriginatingAddress();
                Mensajes = MENSAJE.getDisplayMessageBody().toString();
                // Copio el mensaje tal cual viene para mostrarlo.
                String MsjInformacion = Mensajes;
                //Reemplazo todos los \n por espacios en blanco.
                Mensajes = Mensajes.replace("\n"," ");
                // Separo los mensaje mediante los espacios.
                String SCT[] = Mensajes.split(" ");

                SharedPreferences prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                String CelularDeAlarma = prefs.getString("CelularAlarma","12345");

                //Toast.makeText(context, "Remitente permitido:"+CelularDeAlarma, Toast.LENGTH_LONG).show();

                if (Remitente.substring(Remitente.length()-10).equals(CelularDeAlarma.substring(CelularDeAlarma.length()-10))){
                    if (SCT[0]!=null) {
                        // Tomo la primer palabra del mensaje y me fijo si es algun comando de salida, celular o tarjeta. A la vez en cada uno me fijo de que tipo es.
                        switch (Mensajes.charAt(0)) {
                            // Salidas, primer control de informacion, segundo de configuracion.
                            case 'S': case 's':
                                if (Mensajes.charAt(3) == ' '){
                                String[] salidas = Mensajes.split(",");
                                for (int j = 0; j < 8 ; j++) {
                                    String nombres = salidas[j].substring(4);
                                    String [] salidas2 = nombres.split(" ");
                                    int id = (j + 1);
                                    if (salidas2[0].equals("Salida")){
                                        String[] args = {String.valueOf(id)};
                                        ContentValues values = new ContentValues();
                                        values.put(CBContract.SalidasEntry.COLUMN_NOMBRE, nombres);
                                        values.put(CBContract.SalidasEntry.COLUMN_HABILITADA, "N");
                                        int numRows = db.update(CBContract.SalidasEntry.TABLE_NAME, values, CBContract.SalidasEntry._ID + " =?", args);
                                        Log.d("Update Rows", String.valueOf(numRows));
                                    } else {
                                        String[] args = {String.valueOf(id)};
                                        ContentValues values = new ContentValues();
                                        values.put(CBContract.SalidasEntry.COLUMN_NOMBRE, nombres);
                                        values.put(CBContract.SalidasEntry.COLUMN_HABILITADA, "S");
                                        int numRows = db.update(CBContract.SalidasEntry.TABLE_NAME, values, CBContract.SalidasEntry._ID + " =?", args);
                                        Log.d("Update Rows", String.valueOf(numRows));
                                    }

                                }
                                    Toast.makeText(context, "Salidas Actualizadas.", Toast.LENGTH_LONG).show();
                                    Intent in = new Intent(context, ListaSalidas.class);
                                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(in);
                                    db.close();
                                    break;
                                }else if (Mensajes.charAt(3) == ','){
                                    String[] salidas = Mensajes.split(",");
                                    int id = Integer.parseInt(salidas[0].substring(1));
                                    String[] args = {String.valueOf(id)};
                                    ContentValues values = new ContentValues();
                                    values.put(CBContract.SalidasEntry.COLUMN_HABILITADA, salidas[1]);
                                    values.put(CBContract.SalidasEntry.COLUMN_NEMONICO, salidas[2]);
                                    values.put(CBContract.SalidasEntry.COLUMN_NOMBRE, salidas[3]);
                                    values.put(CBContract.SalidasEntry.COLUMN_TIEMPO, salidas[4]);
                                    int numRows = db.update(CBContract.SalidasEntry.TABLE_NAME, values, CBContract.SalidasEntry._ID+" =?", args);
                                    Log.d("Update Rows", String.valueOf(numRows));
                                    db.close();
                                    break;
                                }
                            // Tarjetas, primer control de informacion, segundo de configuracion.
                            case 'T': case 't':
                                if (Mensajes.charAt(3) == ' '){
                                    String[] tarjetas = Mensajes.split(",");
                                    for (int j = 0; j < 20 ; j++) {
                                        String nombres = tarjetas[j].substring(4);
                                        int id = (j + 1);
                                        String[] args = {String.valueOf(id)};
                                        ContentValues values = new ContentValues();
                                        values.put(CBContract.TagsEntry.COLUMN_TAGNOM, nombres);
                                        int numRows = db.update(CBContract.TagsEntry.TABLE_NAME, values, CBContract.SalidasEntry._ID + " =?", args);
                                        Log.d("Update Rows", String.valueOf(numRows));
                                    }
                                    db.close();
                                    break;
                                } else if (Mensajes.charAt(3) == ','){
                                    String[] tarjetas = Mensajes.split(",");
                                    int id = Integer.parseInt(tarjetas[0].substring(1));
                                    String[] args = {String.valueOf(id)};
                                    ContentValues values = new ContentValues();
                                    values.put(CBContract.TagsEntry.COLUMN_TAGNUM, tarjetas[1]);
                                    values.put(CBContract.TagsEntry.COLUMN_TAGCAT, tarjetas[2]);
                                    values.put(CBContract.TagsEntry.COLUMN_TAGNOM, tarjetas[3]);
                                    int numRows = db.update(CBContract.TagsEntry.TABLE_NAME, values, CBContract.SalidasEntry._ID+" =?", args);
                                    Log.d("Update Rows", String.valueOf(numRows));
                                    db.close();
                                    break;
                                }
                            // Celulares, primer control de informacion, segundo de configuracion.
                            case 'C':case 'c':
                                if (Mensajes.charAt(3) == ' '){
                                String[] celulares = Mensajes.split(",");
                                    for (int j = 0; j < 10 ; j++) {
                                        String nombres = celulares[j].substring(4);
                                        int id = (j + 1);
                                        String[] args = {String.valueOf(id)};
                                        ContentValues values = new ContentValues();
                                        values.put(CBContract.CelularesEntry.COLUMN_CELNOM, nombres);
                                        int numRows = db.update(CBContract.CelularesEntry.TABLE_NAME, values, CBContract.SalidasEntry._ID + " =?", args);
                                        Log.d("Update Rows", String.valueOf(numRows));
                                    }
                                    db.close();
                                    break;
                                } else if (Mensajes.charAt(3) == ','){
                                    String[] celulares = Mensajes.split(",");
                                    int id = Integer.parseInt(celulares[0].substring(1));
                                    String[] args = {String.valueOf(id)};
                                    ContentValues values = new ContentValues();
                                    values.put(CBContract.CelularesEntry.COLUMN_CELNUM, celulares[1]);
                                    values.put(CBContract.CelularesEntry.COLUMN_CELCAT, celulares[2]);
                                    values.put(CBContract.CelularesEntry.COLUMN_CELNOM, celulares[3]);
                                    int numRows = db.update(CBContract.CelularesEntry.TABLE_NAME, values, CBContract.SalidasEntry._ID+" =?", args);
                                    Log.d("Update Rows", String.valueOf(numRows));
                                    db.close();
                                    break;
                                }
                             // Activaciones, tomo los distintos nombres de las distintas activaciones.
                            case '1':
                                String[] activaciones = Mensajes.split(",");
                                    for (int j = 0; j < 4; j++){
                                        String nombres = activaciones[j].substring(2);
                                        Activaciones.add(nombres);
                                    }
                                Activaciones.add("Reactivar");
                                SharedPreferences.Editor  editor = act.edit();
                                editor.putInt("Activacion_size", Activaciones.size());
                                for (i = 0; i < Activaciones.size(); i++){
                                        editor.putString("Activacion_" + i, Activaciones.get(i).toString());
                                }
                                editor.apply();
                                break;

                            default:
                                // Tomo la segunda palabra del mensaje y comparo si es mensaje de estado, mensaje de activacion de la alarma o mensaje de activacion/desactivacion de la alarma.
                                if (SCT.length>=2) {
                                    switch (SCT[1].toLowerCase()) {
                                        case "activada":
                                            Toast.makeText(context, MsjInformacion, Toast.LENGTH_LONG).show();
                                            break;
                                        case "desactivada":
                                            Toast.makeText(context, MsjInformacion, Toast.LENGTH_LONG).show();
                                            break;
                                        case "disparada":
                                            Toast.makeText(context, MsjInformacion, Toast.LENGTH_LONG).show();
                                            break;
                                        case "activo":
                                            Toast.makeText(context, MsjInformacion, Toast.LENGTH_LONG).show();
                                            break;
                                        case "desactivo":
                                            Toast.makeText(context, MsjInformacion, Toast.LENGTH_LONG).show();
                                            break;
                                        case "ha":
                                        case "en":
                                            // create a pending intent
                                            Intent notificationIntent = new Intent(context,
                                                    ReceiverSMS.class);
                                            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                                                    notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

                                            // user builder to prepare notification
                                            Notification.Builder notificationBuilder = new Notification.Builder(
                                                    context)
                                                    .setTicker("Se ha disparado la Alarma")
                                                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                                                    .setAutoCancel(true)
                                                    .setContentTitle("Alerta Seguridad!")
                                                    .setContentText(MsjInformacion)
                                                    .setContentIntent(pendingIntent)
                                                    .setPriority(Notification.PRIORITY_MAX);


                                            // Sonido por defecto de notificaciones
                                            Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                                            notificationBuilder.setSound(defaultSound);

                                            // Patrón de vibración: 2 segundos vibra, 0.5 segundos para, 2 segundos vibra
                                            long[] pattern = new long[]{2000,500,2000};
                                            notificationBuilder.setVibrate(pattern);

                                            // pass the Notification to the NotificationManager
                                            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                                            break;
                                        case "":
                                            break;
                                        default:
                                            break;
                                    }
                                } else {

                                }
                                break;
                        }
                    }

                }
                else {

                }

                   /*
                   if(body.equals("flag".toLowerCase())) {
                        //Toast.makeText(context, "Numero:" + Remitente + "\nMensaje:" + Mensajes, Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "Se reconoce flag", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "No se reconoce comando", Toast.LENGTH_LONG).show();
                    }
                    */
            }
        }

    }

}
