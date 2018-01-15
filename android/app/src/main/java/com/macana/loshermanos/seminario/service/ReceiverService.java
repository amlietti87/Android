package com.macana.loshermanos.seminario.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.content.BroadcastReceiver;

import com.macana.loshermanos.seminario.broadcastReceiver.ReceiverSMS;

/**
 * Created by juan on 04/10/17.
 */

public class ReceiverService extends Service {
    private static final String TAG = "ReceiverService";
    private static BroadcastReceiver ReceiverSMSBroadcastReceiver;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void onCreate() {
        registerReceiverBroadcastReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiverBroadcastReceiver();
        sendBroadcast(new Intent("RestartOurReceiverService"));
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        sendBroadcast(new Intent("RestartOurReceiverService"));
    }

    private void registerReceiverBroadcastReceiver() {
        ReceiverSMSBroadcastReceiver = new ReceiverSMS();
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(ReceiverSMSBroadcastReceiver, filter);
    }

    private void unregisterReceiverBroadcastReceiver() {
        unregisterReceiver(ReceiverSMSBroadcastReceiver);
        ReceiverSMSBroadcastReceiver = null;
    }


}
