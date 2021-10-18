package com.cubezytech.pillsreminder.Pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.util.Log;

public class SharedPrefrance {
    public static final String MyPREFERENCES = "Pills Reminder";
    public static String Flash = "LOGIN";
    public static String NOTIFICATION_TONE = "NOTIFICATION_TONE";
    public static String REFILL_TIME = "REFILL_TIME";
    public static String MED_TIME = "MED_TIME";
    public static String SNOOZE_TIME = "SNOOZE_TIME";
    public static String POPUP = "POPUP";


    public static boolean getLogin(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        boolean ans = sharedpreferences.getBoolean(Flash, false);
        return ans;
    }

    public static void setLogin(Context c1, boolean value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Flash, value);
        editor.apply();
    }

    public static String getNotificationTone(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(NOTIFICATION_TONE, String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)));
        Log.e("LLLL_Noti_Tone: ", ans);
        return ans;
    }

    public static void setNotificationTone(Context c1, String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(NOTIFICATION_TONE, value);
        editor.apply();
    }

    public static String getRefillTime(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(REFILL_TIME, "10");

        return ans;
    }

    public static void setRefillTimee(Context c1, String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(REFILL_TIME, value);
        editor.apply();
    }

    public static String getMedReminder(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(MED_TIME, "4");

        return ans;
    }

    public static void setMedReminder(Context c1, String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(MED_TIME, value);
        editor.apply();
    }

    public static String getSnoozeTime(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(SNOOZE_TIME, "10");

        return ans;
    }

    public static void setSnoozeTime(Context c1, String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SNOOZE_TIME, value);
        editor.apply();
    }

    public static String getPopup(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(POPUP, "No popup");

        return ans;
    }

    public static void setPopup(Context c1, String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(POPUP, value);
        editor.apply();
    }

}
