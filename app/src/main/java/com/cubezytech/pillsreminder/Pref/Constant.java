package com.cubezytech.pillsreminder.Pref;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.cubezytech.pillsreminder.Model.TimeInDay;
import com.cubezytech.pillsreminder.Model.TimeInWeek;

import java.util.ArrayList;

public class Constant {

    public static String MISSED = "Missed";
    public static String TAKEN = "Taken";

    public static boolean IS_YES = true;
    public static boolean IS_CUSTOM = false;
    public static boolean IS_OTHER = false;
    public static boolean IS_EVERY_DAY_YES = false;
    public static Integer WEEK_DAYS = 1;
    public static Integer TIME_IN_DAYS = 1;
    public static Integer TIME_IN_DAYS_DONE = 0;

    public static Integer TIME_IN_WEEK_DONE = 0;
    public static Integer EVERY_NUM_HOURS = 1;
    public static ArrayList<String> DAYSLIST = new ArrayList<>();
    public static ArrayList<TimeInDay> TIMEINDAYSLIST = new ArrayList<>();
    public static ArrayList<TimeInWeek> TIMEINWEEKLIST = new ArrayList<>();

    public static int NOTIFICATION_ID = 0;

    public static void clearData() {
        IS_YES = true;
        IS_CUSTOM = false;
        IS_OTHER = false;
        IS_EVERY_DAY_YES = false;
        WEEK_DAYS = 1;
        TIME_IN_DAYS = 1;
        TIME_IN_DAYS_DONE = 0;
        DAYSLIST = new ArrayList<>();
        TIMEINDAYSLIST = new ArrayList<>();
        TIMEINWEEKLIST = new ArrayList<>();
    }

    public static void blurBitmapWithRenderscript(
            RenderScript rs, Bitmap bitmap2) {
        // this will blur the bitmapOriginal with a radius of 25
        // and save it in bitmapOriginal
        // use this constructor for best performance, because it uses
        // USAGE_SHARED mode which reuses memory
        final Allocation input =
                Allocation.createFromBitmap(rs, bitmap2);
        final Allocation output = Allocation.createTyped(rs,
                input.getType());
        final ScriptIntrinsicBlur script =
                ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // must be >0 and <= 25
        script.setRadius(25f);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap2);
    }


}
