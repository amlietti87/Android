package com.macana.loshermanos.seminario.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.macana.loshermanos.seminario.service.ReceiverService;

/**
 * Created by juan on 04/10/17.
 */

public class RestartReceiverService extends BroadcastReceiver
{

    private static final String TAG = "RestartReceiverService";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");
        context.startService(new Intent(context.getApplicationContext(), ReceiverService.class));

    }

}
