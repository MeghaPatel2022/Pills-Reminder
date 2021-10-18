package com.cubezytech.pillsreminder.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.cubezytech.pillsreminder.R;

public class PopupService extends Service {

    private static final String TAG = PopupService.class.getSimpleName();
    WindowManager mWindowManager;
    View mView;
    Animation mAnimation;

    private final BroadcastReceiver overlayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("LLLL_Mes: ", "[onReceive]" + action);
            showPopup();
            if (action.equals(Intent.ACTION_SCREEN_ON)) {
                showPopup();
            } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
                hidePopup();
            } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                hidePopup();
            }
        }
    };
    private boolean isRunning;

    @Override
    public void onCreate() {
        super.onCreate();
        this.isRunning = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerOverlayReceiver();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showPopup() {
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mView = View.inflate(getApplicationContext(), R.layout.dialoge_pop_up, null);
        mView.setTag(TAG);

        mView.setVisibility(View.VISIBLE);

        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT);

        mView.setVisibility(View.VISIBLE);
        mAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        mView.startAnimation(mAnimation);

        mWindowManager.addView(mView, mLayoutParams);
    }

    private void hidePopup() {
        if (mView != null && mWindowManager != null) {
            mWindowManager.removeView(mView);
            mView = null;
        }
    }

    private void registerOverlayReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(overlayReceiver, filter);
    }

    private void unregisterOverlayReceiver() {
        hidePopup();
        try {
            if (overlayReceiver != null)
                unregisterReceiver(overlayReceiver);

        } catch (Exception e) {
        }

    }

    @Override
    public void onDestroy() {
        unregisterOverlayReceiver();
        super.onDestroy();
    }

}
