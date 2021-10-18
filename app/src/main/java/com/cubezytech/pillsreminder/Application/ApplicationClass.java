package com.cubezytech.pillsreminder.Application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.lifecycle.LifecycleObserver;

import com.cubezytech.pillsreminder.Receiver.AlarmReceiver;

import java.text.SimpleDateFormat;

import static android.os.Build.VERSION.SDK_INT;

public class ApplicationClass extends Application implements LifecycleObserver {

    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
//        callService();
    }

    private void callService() {

        Intent alarm = new Intent(mContext, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(mContext, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), System.currentTimeMillis() * 60000, pendingIntent);

            long when = System.currentTimeMillis() + 5000;
            if (SDK_INT < Build.VERSION_CODES.KITKAT)
                alarmManager.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);
            else if (SDK_INT < Build.VERSION_CODES.M)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, when, pendingIntent);
            else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, when, pendingIntent);
            }
        }

    }

}
