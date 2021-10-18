package com.cubezytech.pillsreminder.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.cubezytech.pillsreminder.Activity.MainActivity;
import com.cubezytech.pillsreminder.Pref.Constant;
import com.cubezytech.pillsreminder.Pref.SharedPrefrance;
import com.cubezytech.pillsreminder.R;
import com.cubezytech.pillsreminder.Receiver.AlarmReceiver;

import java.text.SimpleDateFormat;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static android.os.Build.VERSION.SDK_INT;

public class BackgroundService extends Service {
    Thread backgroundThread = null;
    private boolean isRunning;
    private Context context;

    private final Runnable myTask = new Runnable() {
        public void run() {

            sendNotification(context, "Please take your medicines....");

            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");

            Log.e("LLLLLL_time: ", dateFormat1.format(System.currentTimeMillis()));
            Intent alarm = new Intent(context, AlarmReceiver.class);
            boolean alarmRunning = (PendingIntent.getBroadcast(context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);

            Log.e("LLLL_Alarm_run: ", String.valueOf(alarmRunning));

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), System.currentTimeMillis() * 60000, pendingIntent);

            long when = System.currentTimeMillis() + 60000;
            if (SDK_INT < Build.VERSION_CODES.KITKAT)
                alarmManager.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);
            else if (SDK_INT < Build.VERSION_CODES.M)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, when, pendingIntent);
            else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, when, pendingIntent);
            }
            // Do something here
            stopSelf();
        }
    };
    private final BroadcastReceiver overlayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("LLLL_Mes: ", "[onReceive]" + action);

            if (!isRunning) {
                isRunning = true;
                backgroundThread.start();
            }
        }
    };

    public static void sendNotification(Context mcontext, String messageBody) {
        Intent intent = new Intent(mcontext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mcontext, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);


        AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_COMMUNICATION_INSTANT)
                .build();

        Log.e("LLLL_Ringtone: ", SharedPrefrance.getNotificationTone(mcontext));
        Ringtone r = RingtoneManager.getRingtone(mcontext, Uri.parse(SharedPrefrance.getNotificationTone(mcontext)));
        r.play();
        Uri defaultSoundUri = Uri.parse(SharedPrefrance.getNotificationTone(mcontext));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(mcontext.getString(R.string.default_notification_channel_id), "Rewards Notifications", IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setVibrationPattern(new long[]{500, 500, 500, 500, 500, 500, 500, 500, 500});
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setSound(null, null);
            notificationChannel.setSound(defaultSoundUri, audioAttributes);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mcontext, mcontext.getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.img_next)
                .setContentTitle(mcontext.getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(null)
                .setSound(defaultSoundUri, AudioManager.STREAM_NOTIFICATION)
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500, 500, 500})
                .setContentIntent(pendingIntent);

        Log.e("LLLL_Notification_ID: ", String.valueOf(Constant.NOTIFICATION_ID));
        notificationManager.notify(Constant.NOTIFICATION_ID, notificationBuilder.build());
        Constant.NOTIFICATION_ID++;
    }

    BackgroundService getService() {
        return BackgroundService.this;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerOverlayReceiver();
        return START_STICKY;
    }

    private void registerOverlayReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(overlayReceiver, filter);
    }

    public class MainServiceBinder extends Binder {
        public BackgroundService getService() {
            return BackgroundService.this;
        }
    }

}
