package com.macana.loshermanos.seminario.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.macana.loshermanos.seminario.R;

public class SendSMS {


    public static void SendSMS (Context context, String phoneNumberDst, String mensaje){
        try {
            String message = mensaje;
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumberDst,null,message,null,null);
            Toast.makeText(context, "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(context, "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
