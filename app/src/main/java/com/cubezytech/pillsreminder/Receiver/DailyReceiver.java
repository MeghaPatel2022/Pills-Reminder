package com.cubezytech.pillsreminder.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cubezytech.pillsreminder.service.BackgroundService;

public class DailyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Quote in Morning
            Intent background = new Intent(context, BackgroundService.class);
            context.startService(background);
        }
    }
}
