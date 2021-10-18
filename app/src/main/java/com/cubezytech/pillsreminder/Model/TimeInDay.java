package com.cubezytech.pillsreminder.Model;

public class TimeInDay {

    String R_id, Med_Name, Med_Strength, Med_type, Time_In_day, WeekDays, pills = "1", time, date, taken, taken_time;

    public String getR_id() {
        return R_id;
    }

    public void setR_id(String r_id) {
        R_id = r_id;
    }

    public String getMed_Name() {
        return Med_Name;
    }

    public void setMed_Name(String medi_Name) {
        Med_Name = medi_Name;
    }

    public String getMed_type() {
        return Med_type;
    }

    public void setMed_type(String med_type) {
        Med_type = med_type;
    }

    public String getMed_Strength() {
        return Med_Strength;
    }

    public void setMed_Strength(String med_Strength) {
        Med_Strength = med_Strength;
    }

    public String getTime_In_day() {
        return Time_In_day;
    }

    public void setTime_In_day(String time_In_day) {
        Time_In_day = time_In_day;
    }

    public String getWeekDays() {
        return WeekDays;
    }

    public void setWeekDays(String weekDays) {
        WeekDays = weekDays;
    }

    public String getPills() {
        return pills;
    }

    public void setPills(String pills) {
        this.pills = pills;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public String getTaken_time() {
        return taken_time;
    }

    public void setTaken_time(String taken_time) {
        this.taken_time = taken_time;
    }
}
