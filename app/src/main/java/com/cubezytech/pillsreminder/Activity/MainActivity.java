package com.cubezytech.pillsreminder.Activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cubezytech.pillsreminder.DBHelper.DBHelper;
import com.cubezytech.pillsreminder.Fragment.MainFragment.Main2Fragment;
import com.cubezytech.pillsreminder.Fragment.MainFragment.MainFragment;
import com.cubezytech.pillsreminder.R;
import com.cubezytech.pillsreminder.Receiver.AlarmReceiver;
import com.cubezytech.pillsreminder.service.BackgroundService;

import java.io.File;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rl_main)
    RelativeLayout rl_main;
    ServiceConnection sc;
    DBHelper dbHelper;
    private BackgroundService backgroundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(MainActivity.this);
        dbHelper = new DBHelper(MainActivity.this);

        // start service of popup service
//        if (SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(MainActivity.this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 1234);
//            } else {
//                stopService(new Intent(getApplicationContext(), PopupService.class));
//                Intent intent1 = new Intent(getApplicationContext(), PopupService.class);
//                if (SDK_INT >= Build.VERSION_CODES.O) {
//                    startForegroundService(intent1);
//                }
//            }
//        }

        Intent alarm = new Intent(MainActivity.this, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(MainActivity.this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarm, 0);
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

        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                backgroundService = ((BackgroundService.MainServiceBinder) service).getService();
                Log.e("LLL_Service", "[MainActivity]: onServiceConnected()");
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                Log.e("LLL_Service", "[MainActivity]: onServiceDisconnected()");
            }
        };

        bindService(new Intent(this, BackgroundService.class), sc, Context.BIND_AUTO_CREATE);
        addFragment();

       /* // Get installed APK List and it's logo.
        final PackageManager pm = getPackageManager();
       //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Log.e("LLLLL_APP", "Installed package :" + packageInfo.packageName);
            Log.e("LLLLL_APP", "Source dir : " + packageInfo.sourceDir);
            Log.e("LLLLL_APP", "Source icon : " + packageInfo.icon);
            Log.e("LLLLL_APP", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
            try {
                Drawable icon = getPackageManager().getApplicationIcon(packageInfo.packageName);
                Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
                Log.e("LLLLL_APP", "Source icon : " + icon);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(sc);
    }

    private void addFragment() {

        if (!dbHelper.getPillsNoRecords().isEmpty() || !dbHelper.getPillsNoRecordsYes().isEmpty()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_main, new Main2Fragment(), "Main2Fragment")
                    .disallowAddToBackStack()
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_main, new MainFragment(), "MainFragment")
                    .disallowAddToBackStack()
                    .commit();
        }

    }

}