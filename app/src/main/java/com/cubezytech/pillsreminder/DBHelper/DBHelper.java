package com.cubezytech.pillsreminder.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cubezytech.pillsreminder.Model.TimeInDay;
import com.cubezytech.pillsreminder.Model.TimeInWeek;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Database Name
    public static final String DATABASE_NAME = "PillsReminder.db";
    private static final int DATABASE_VERSION = 1;

    // Table_Pills_Details_No TableColumns names
    private static final String _ID = "_Id";
    private static final String R_ID = "R_Id";
    private static final String MED_NAME = "Med_Name";
    private static final String MED_STRENGTH = "Med_Strength";
    private static final String MED_TYPE = "Med_Type";
    private static final String TIME_IN_DAY = "Time_In_Day";
    private static final String WEEK_DAYS = "Week_Days";
    private static final String PILLS = "Pills";
    private static final String TIME_TO_TAKE = "Time_to_take";
    private static final String DATE = "Date";
    private static final String TAKEN = "Taken";
    private static final String TAKEN_TIME = "Taken_time";

    // Table_Pills_Details_Yes TableColumns names
    private static final String ID = "_Id";
    private static final String Y_ID = "R_Id";
    private static final String MED_NAME_Y = "Med_Name";
    private static final String MED_STRENGTH_Y = "Med_Strength";
    private static final String MED_TYPE_Y = "Med_Type";
    private static final String WEEK_DAYS_IN = "Week_Days_In";
    private static final String DAYS = "Days";
    private static final String TIME_IN_DAY_Y = "Time_In_Day";
    private static final String PILLS_Y = "Pills";
    private static final String TIME_TO_TAKE_Y = "Time_to_take";
    private static final String DATE_Y = "Date";
    private static final String TAKEN_Y = "Taken";
    private static final String TAKEN_TIME_Y = "Taken_time";

    // Pills Details Table Name
    public static String TABLE_PILLS_DETAILS_NO = "Table_Pills_Details_No";
    public static String TABLE_PILLS_DETAILS_YES = "Table_Pills_Details_Yes";
    Context context;

    //2nd table for appointment
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATABASE_TABLE_PILLS_DETAILS = "CREATE TABLE " + TABLE_PILLS_DETAILS_NO + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                R_ID + " TEXT," +
                MED_NAME + " TEXT," +
                MED_STRENGTH + " TEXT," +
                MED_TYPE + " TEXT," +
                TIME_IN_DAY + " TEXT," +
                WEEK_DAYS + " TEXT," +
                PILLS + " TEXT," +
                TIME_TO_TAKE + " TEXT," +
                DATE + " TEXT," +
                TAKEN + " TEXT," +
                TAKEN_TIME + " TEXT);";

        String CREATE_DATABASE_TABLE_PILLS_DETAILS_Y = "CREATE TABLE " + TABLE_PILLS_DETAILS_YES + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Y_ID + " TEXT," +
                MED_NAME_Y + " TEXT," +
                MED_STRENGTH_Y + " TEXT," +
                MED_TYPE_Y + " TEXT," +
                WEEK_DAYS_IN + " TEXT," +
                DAYS + " TEXT," +
                TIME_IN_DAY_Y + " TEXT," +
                PILLS_Y + " TEXT," +
                TIME_TO_TAKE_Y + " TEXT," +
                DATE_Y + " TEXT," +
                TAKEN_Y + " TEXT," +
                TAKEN_TIME_Y + " TEXT);";

        db.execSQL(CREATE_DATABASE_TABLE_PILLS_DETAILS);
        db.execSQL(CREATE_DATABASE_TABLE_PILLS_DETAILS_Y);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PILLS_DETAILS_NO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PILLS_DETAILS_YES);
    }

    // Methods For Table_Pills_Details_No
    public boolean insertNoPillsDetails(String _id, String med_name, String med_strength, String med_type, String time_in_day, String week_days, String pills, String time_to_take, String date, String taken, String taken_time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(R_ID, _id);
        values.put(MED_NAME, med_name);
        values.put(MED_STRENGTH, med_strength);
        values.put(MED_TYPE, med_type);
        values.put(TIME_IN_DAY, week_days);
        values.put(WEEK_DAYS, time_in_day);
        values.put(PILLS, pills);
        values.put(TIME_TO_TAKE, time_to_take);
        values.put(DATE, date);
        values.put(TAKEN, taken);
        values.put(TAKEN_TIME, taken_time);

        long result = db.insert(TABLE_PILLS_DETAILS_NO, null, values);
        db.close();
        return result != -1;
    }

    public ArrayList<TimeInDay> getPillsNoRecords() {

        ArrayList<TimeInDay> timeInDays = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_PILLS_DETAILS_NO, null);

        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {

                TimeInDay timeInDay = new TimeInDay();
                timeInDay.setR_id(cur.getString(1));
                timeInDay.setMed_Name(cur.getString(2));
                timeInDay.setMed_Strength(cur.getString(3));
                timeInDay.setMed_type(cur.getString(4));
                timeInDay.setTime_In_day(cur.getString(5));
                timeInDay.setWeekDays(cur.getString(6));
                timeInDay.setPills(cur.getString(7));
                timeInDay.setTime(cur.getString(8));
                timeInDay.setDate(cur.getString(9));
                timeInDay.setTaken(cur.getString(10));
                timeInDay.setTaken_time(cur.getString(11));

                timeInDays.add(timeInDay);
            } while (cur.moveToNext());
            cur.close();
        }

        return timeInDays;
    }

    public TimeInDay getPillsNoRecord(String id, String date, String time) {

        TimeInDay timeInDay1 = new TimeInDay();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_PILLS_DETAILS_NO, null);

        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {

                if (id.equals(cur.getString(1)) && time.equals(cur.getString(8)) && date.equals(cur.getString(9))) {
                    TimeInDay timeInDay = new TimeInDay();
                    timeInDay.setR_id(cur.getString(1));
                    timeInDay.setMed_Name(cur.getString(2));
                    timeInDay.setMed_Strength(cur.getString(3));
                    timeInDay.setMed_type(cur.getString(4));
                    timeInDay.setTime_In_day(cur.getString(5));
                    timeInDay.setWeekDays(cur.getString(6));
                    timeInDay.setPills(cur.getString(7));
                    timeInDay.setTime(cur.getString(8));
                    timeInDay.setDate(cur.getString(9));
                    timeInDay.setTaken(cur.getString(10));
                    timeInDay.setTaken_time(cur.getString(11));

                    timeInDay1 = timeInDay;

                    Log.e("LLLL_Data: id", id + " : " + cur.getString(1));
                    Log.e("LLLL_Data: date", date + " : " + cur.getString(8));
                    Log.e("LLLL_Data: time", time + " : " + cur.getString(9));
                }

            } while (cur.moveToNext());
            cur.close();
        }

        return timeInDay1;
    }


    public boolean updatePillsNoRecords(String _id, String med_strength, String pills, String date, String time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MED_STRENGTH, med_strength);
        values.put(PILLS, pills);

        db.update(TABLE_PILLS_DETAILS_NO, values, R_ID + " = ? and " + DATE + " = ? and " + TIME_TO_TAKE + " = ? ", new String[]{_id, date, time});
        return true;
    }

    public boolean updatePillsNoRecordsTaken(String _id, String taken, String date, String time, String take_time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TAKEN, taken);
        values.put(TAKEN_TIME, take_time);

        db.update(TABLE_PILLS_DETAILS_NO, values, R_ID + " = ? and " + DATE + " = ? and " + TIME_TO_TAKE + " = ? ", new String[]{_id, date, time});
        return true;
    }

    // Delete all chats in the database
    public void deletePillsNoRecords(String r_id, String date, String time) {
        try {
            // Order of deletions is important when foreign key relationships exist.
            String deleteQuery = "DELETE FROM " + TABLE_PILLS_DETAILS_NO + " WHERE " + R_ID +
                    "='" + r_id + "' AND " + TIME_TO_TAKE +
                    "='" + time + "'" + " AND " + DATE + "='" + date + "'";

            getWritableDatabase().execSQL(deleteQuery);
        } catch (Exception e) {
            Log.e("LLLLL_DB_DELETE", "deleteAllChats: " + e.getMessage());
        }
    }


    // Methods For Table_Pills_Details_No
    public boolean insertNoPillsDetailsYes(String _id, String med_name, String med_strength, String med_type, String days, String time_in_day, String week_days, String pills, String time_to_take, String date, String taken, String taken_time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Y_ID, _id);
        values.put(MED_NAME_Y, med_name);
        values.put(MED_STRENGTH_Y, med_strength);
        values.put(MED_TYPE_Y, med_type);
        values.put(WEEK_DAYS_IN, week_days);
        values.put(DAYS, days);
        values.put(TIME_IN_DAY_Y, time_in_day);
        values.put(PILLS_Y, pills);
        values.put(TIME_TO_TAKE_Y, time_to_take);
        values.put(DATE_Y, date);
        values.put(TAKEN_Y, taken);
        values.put(TAKEN_TIME_Y, taken_time);

        long result = db.insert(TABLE_PILLS_DETAILS_YES, null, values);
        db.close();
        return result != -1;
    }

    public ArrayList<TimeInWeek> getPillsNoRecordsYes() {

        ArrayList<TimeInWeek> timeInDays = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_PILLS_DETAILS_YES, null);

        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {

                TimeInWeek timeInDay = new TimeInWeek();
                timeInDay.setY_id(cur.getString(1));
                timeInDay.setMed_name(cur.getString(2));
                timeInDay.setMed_strength(cur.getString(3));
                timeInDay.setMed_type(cur.getString(4));
                timeInDay.setWeek_days_in(cur.getString(5));
                timeInDay.setDay(cur.getString(6));
                timeInDay.setTime_in_days(cur.getString(7));
                timeInDay.setPills(cur.getString(8));
                timeInDay.setTime(cur.getString(9));
                timeInDay.setDate(cur.getString(10));
                timeInDay.setTaken(cur.getString(11));
                timeInDay.setTaken_time(cur.getString(12));

                timeInDays.add(timeInDay);
            } while (cur.moveToNext());
            cur.close();
        }

        return timeInDays;
    }

    public TimeInWeek getPillsNoRecordYes(String id, String date, String time) {

        TimeInWeek timeInDay1 = new TimeInWeek();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_PILLS_DETAILS_YES, null);

        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {

                if (id.equals(cur.getString(1)) &&
                        time.equals(cur.getString(9)) &&
                        date.equals(cur.getString(10))) {
                    TimeInWeek timeInDay = new TimeInWeek();
                    timeInDay.setY_id(cur.getString(1));
                    timeInDay.setMed_name(cur.getString(2));
                    timeInDay.setMed_strength(cur.getString(3));
                    timeInDay.setMed_type(cur.getString(4));
                    timeInDay.setWeek_days_in(cur.getString(5));
                    timeInDay.setDay(cur.getString(6));
                    timeInDay.setTime_in_days(cur.getString(7));
                    timeInDay.setPills(cur.getString(8));
                    timeInDay.setTime(cur.getString(9));
                    timeInDay.setDate(cur.getString(10));
                    timeInDay.setTaken(cur.getString(11));
                    timeInDay.setTaken_time(cur.getString(12));

                    timeInDay1 = timeInDay;
                }
            } while (cur.moveToNext());
            cur.close();
        }

        return timeInDay1;
    }


    public boolean updateNoPillsDetailsYes(String _id, String med_strength, String pills, String date, String time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MED_STRENGTH_Y, med_strength);
        values.put(PILLS_Y, pills);

        db.update(TABLE_PILLS_DETAILS_YES, values, Y_ID + " = ? and " + DATE_Y + " = ? and " + TIME_TO_TAKE_Y + " = ? ", new String[]{_id, date, time});
        return true;
    }

    public boolean updateNoPillsDetailsYesTaken(String _id, String taken, String date, String time, String take_time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TAKEN_Y, taken);
        values.put(TAKEN_TIME_Y, take_time);

        db.update(TABLE_PILLS_DETAILS_YES, values, Y_ID + " = ? and " + DATE_Y + " = ? and " + TIME_TO_TAKE_Y + " = ? ", new String[]{_id, date, time});
        return true;
    }


    // Delete all chats in the database
    public void deletePillsRecordsYes(String r_id, String date, String time) {
        try {
            // Order of deletions is important when foreign key relationships exist.
            String deleteQuery = "DELETE FROM " + TABLE_PILLS_DETAILS_YES + " WHERE " + Y_ID +
                    "='" + r_id + "' AND " + TIME_TO_TAKE_Y +
                    "='" + time + "'" + " AND " + DATE_Y + "='" + date + "'";
            getWritableDatabase().execSQL(deleteQuery);

        } catch (Exception e) {
            Log.e("LLLLL_DB_DELETE", "deleteAllChats: " + e.getMessage());
        }
    }
}
