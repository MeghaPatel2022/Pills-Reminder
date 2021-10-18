package com.cubezytech.pillsreminder.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cubezytech.pillsreminder.service.PopupService;

public class PopupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, PopupService.class);
        context.startService(background);
    }
}
